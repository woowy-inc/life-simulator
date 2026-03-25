package ru.woowy.character.infrastructure.messaging

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import ru.woowy.domain.model.CharacterCreatedEvent
import ru.woowy.domain.model.CharacterDeletedEvent
import ru.woowy.domain.model.Event
import ru.woowy.infrastructure.messaging.KafkaEventPublisher
import ru.woowy.messaging.KafkaTopic

@Component
class CharacterEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Event>,
) : KafkaEventPublisher(kafkaTemplate) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleCharacterCreated(event: CharacterCreatedEvent) {
        sendCharacterEvent(event.characterId.toString(), event)
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleCharacterDeleted(event: CharacterDeletedEvent) {
        sendCharacterEvent(event.characterId.toString(), event)
    }

    private fun sendCharacterEvent(
        key: String,
        event: Event,
    ) {
        sendEvent(KafkaTopic.CHARACTER_EVENTS, key, event)
    }
}