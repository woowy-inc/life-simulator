package ru.woowy.infrastructure.messaging

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.woowy.domain.model.CharacterDeletedEvent
import ru.woowy.domain.model.WorldTickEvent
import ru.woowy.domain.usecase.NeedUseCase
import ru.woowy.extension.classLogger
import ru.woowy.messaging.KafkaTopic

@Component
class NeedEventListener(
    private val needUseCase: NeedUseCase,
) {
    private val logger = classLogger()

    @KafkaListener(topics = [KafkaTopic.WORLD_TICK_EVENTS])
    suspend fun handleWorldTick(
        @Payload event: WorldTickEvent,
        ack: Acknowledgment,
    ) {
        needUseCase.processTick(event.characterId, event.tickNumber, event.gameSpeed)
        ack.acknowledge()
    }

    @KafkaListener(topics = [KafkaTopic.CHARACTER_EVENTS])
    suspend fun handleCharacterDeleted(
        @Payload event: CharacterDeletedEvent,
        ack: Acknowledgment,
    ) {
        logger.info("Received world created event $event")
        needUseCase.delete(event.characterId)
        ack.acknowledge()
    }
}