package ru.woowy.character.infrastructure.messaging

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.woowy.character.domain.usecase.CharacterUseCase
import ru.woowy.domain.model.WorldCreatedEvent
import ru.woowy.domain.model.WorldTickEvent
import ru.woowy.extension.classLogger
import ru.woowy.messaging.KafkaTopic

@Component
class CharacterEventListener(
    private val characterUseCase: CharacterUseCase,
) {
    private val logger = classLogger()

    @KafkaListener(topics = [KafkaTopic.WORLD_EVENTS])
    fun handleWorldCreated(
        @Payload event: WorldCreatedEvent,
        ack: Acknowledgment,
    ) {
        logger.info("Received world created event $event")

        characterUseCase.update(event.characterId, event.worldId)
        ack.acknowledge()
    }

    @KafkaListener(topics = [KafkaTopic.WORLD_TICK_EVENTS])
    fun handleWorldTick(
        @Payload event: WorldTickEvent,
        ack: Acknowledgment,
    ) {
        logger.info("Received world tick event $event")

        ack.acknowledge()
    }
}