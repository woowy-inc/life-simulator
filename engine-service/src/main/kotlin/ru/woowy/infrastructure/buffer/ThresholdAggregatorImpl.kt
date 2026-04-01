package ru.woowy.infrastructure.buffer

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import ru.woowy.domain.buffer.StateHolder
import ru.woowy.domain.buffer.ThresholdAggregator
import ru.woowy.domain.model.CharacterKey
import ru.woowy.domain.model.CharacterState
import ru.woowy.domain.model.CharacterStateBuilder
import ru.woowy.domain.model.NeedUpdatedEvent
import ru.woowy.domain.model.TickableEvent
import ru.woowy.domain.model.WorldTickEvent
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.lifecycle.ServiceScope
import kotlin.time.Duration.Companion.milliseconds

@Component
class ThresholdAggregatorImpl(
    private val holder: StateHolder<CharacterKey, CharacterStateBuilder>,
    private val scope: ServiceScope,
) : ThresholdAggregator {
    companion object {
        private const val THRESHOLD_IN_MS = 300
    }

    override suspend fun threshold(
        characterId: CharacterId,
        event: TickableEvent,
        onRelease: (CharacterState) -> Unit,
    ) {
        val key = CharacterKey(characterId, event.tickNumber)
        val isNew = holder.putIfAbsent(key, CharacterStateBuilder()) == null
        val builder = holder.get(key) ?: return

        when (event) {
            is WorldTickEvent -> {
                builder.game(event)
            }

            is NeedUpdatedEvent -> {
                builder.need(event)
            }
        }

        if (isNew) {
            scope.launch {
                delay(THRESHOLD_IN_MS.milliseconds)
                val builder = holder.remove(key) ?: return@launch
                onRelease(builder.build())
            }
        }
    }
}