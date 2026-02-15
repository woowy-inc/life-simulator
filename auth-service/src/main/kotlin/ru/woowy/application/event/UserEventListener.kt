package ru.woowy.application.event

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.game.KafkaTopic

@Component
internal class UserEventListener(
    private val kafkaTemplate: KafkaTemplate<String, Event>,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleUserRegistered(event: UserRegisteredEvent) {
        try {
            val future = kafkaTemplate.send(KafkaTopic.USER_EVENTS, event.user.id, event)

            future.whenComplete { _, ex ->
                if (ex != null) {
                    logger.error("Failed to send event", ex)
                } else {
                    logger.info("Event sent successfully!")
                }
            }
        } catch (ex: Exception) {
            logger.error("Listener exception", ex)
        }
    }
}