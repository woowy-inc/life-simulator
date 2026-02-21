package ru.woowy.application.event

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import ru.woowy.domain.model.EmailVerifyEvent
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.game.KafkaTopic

@Component
internal class UserEventListener(
    private val kafkaTemplate: KafkaTemplate<String, Event>,
) {
    private val logger = LoggerFactory.getLogger(UserEventListener::class.java)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleUserRegistered(event: UserRegisteredEvent) {
        sendEvent(KafkaTopic.USER_EVENTS, event.userId, event)
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleEmailVerify(event: EmailVerifyEvent) {
        sendEvent(KafkaTopic.USER_EVENTS, event.email, event)
    }

    private fun sendEvent(
        topic: String,
        key: String,
        event: Event,
    ) {
        try {
            kafkaTemplate.send(topic, key, event).whenComplete { _, ex ->
                if (ex != null) {
                    logger.error("Failed to send event: $event", ex)
                } else {
                    logger.info("Event sent successfully: $event")
                }
            }
        } catch (ex: Exception) {
            logger.error("Listener exception for event: $event", ex)
        }
    }
}