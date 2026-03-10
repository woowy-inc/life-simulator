package ru.woowy.infrastructure.messaging

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.woowy.domain.model.CharacterCreatedEvent
import ru.woowy.domain.model.CharacterDeletedEvent
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.WorldRequest
import ru.woowy.domain.usecase.WorldUseCase
import ru.woowy.extension.classLogger
import ru.woowy.messaging.KafkaTopic

@Component
class CharacterEventListener(
    private val worldUseCase: WorldUseCase,
) {
    private val logger = classLogger()

    @KafkaListener(topics = [KafkaTopic.CHARACTER_EVENTS])
    fun handle(
        @Payload event: Event,
        ack: Acknowledgment,
    ) {
        when (event) {
            is CharacterCreatedEvent -> handleCharacterCreated(event)
            is CharacterDeletedEvent -> handleCharacterDeleted(event)
            else -> logger.warn("No handler found for event: $event")
        }

        ack.acknowledge()
    }

    private fun handleCharacterCreated(event: CharacterCreatedEvent) {
        logger.info("Received character created event $event")
        worldUseCase.add(WorldRequest(event.characterId))
    }

    private fun handleCharacterDeleted(event: CharacterDeletedEvent) {
        logger.info("Received character deleted event $event")
        worldUseCase.deleteByCharacter(event.characterId)
    }
}