package ru.woowy.infrastructure.messaging

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.WorldCreatedEvent
import ru.woowy.messaging.KafkaTopic

@Component
class WorldEventPublisher(
    kafkaTemplate: KafkaTemplate<String, Event>,
) : KafkaEventPublisher(kafkaTemplate) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleWorldCreated(event: WorldCreatedEvent) {
        sendEvent(KafkaTopic.WORLD_EVENTS, event.worldId.toString(), event)
    }
}