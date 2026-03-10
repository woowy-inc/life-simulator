package ru.woowy.infrastructure.messaging

import org.springframework.kafka.core.KafkaTemplate
import ru.woowy.domain.model.Event
import ru.woowy.extension.classLogger

abstract class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Event>,
) {
    private val logger = classLogger()

    protected fun sendEvent(
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