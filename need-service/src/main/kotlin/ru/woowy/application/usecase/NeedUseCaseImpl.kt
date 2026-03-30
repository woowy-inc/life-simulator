package ru.woowy.application.usecase

import jakarta.annotation.PreDestroy
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.woowy.domain.calculation.NeedCalculator
import ru.woowy.domain.holder.NeedHolder
import ru.woowy.domain.messaging.EventPublisher
import ru.woowy.domain.model.Need
import ru.woowy.domain.repository.NeedRepository
import ru.woowy.domain.usecase.NeedUseCase
import ru.woowy.game.GameSpeed
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.flush.FlushPolicy
import ru.woowy.infrastructure.flush.Flushable
import ru.woowy.infrastructure.lifecycle.ServiceScope
import ru.woowy.infrastructure.mapper.asUpdatedEvent

@Service
class NeedUseCaseImpl(
    private val needRepository: NeedRepository,
    private val needCalculator: NeedCalculator,
    private val needHolder: NeedHolder,
    private val eventPublisher: EventPublisher,
    private val scope: ServiceScope,
) : Flushable(policy = FlushPolicy.everyNTimes(3)),
    NeedUseCase {
    override suspend fun getNeed(characterId: CharacterId): Need = needHolder.get(characterId)
        ?: needRepository.findLast(characterId)
        ?: needRepository.add(characterId, Need())

    override suspend fun processTick(
        characterId: CharacterId,
        tickNumber: Long,
        gameSpeed: GameSpeed,
    ) {
        val need = getNeed(characterId)
        val calculated = needCalculator.applyModifier(need, gameSpeed)

        scope.launch {
            needHolder.push(characterId, calculated)
            notifyFlush()
        }

        scope.launch {
            eventPublisher.publish(calculated.asUpdatedEvent(characterId, tickNumber))
        }
    }

    override suspend fun flush() {
        needHolder.popAll().forEach { (characterId, need) ->
            scope.launch {
                needRepository.add(characterId, need)
            }
        }
    }

    @PreDestroy
    fun destroy() {
        runBlocking { flush() }
    }
}