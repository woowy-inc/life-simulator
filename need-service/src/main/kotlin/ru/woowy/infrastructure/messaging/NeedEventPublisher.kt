package ru.woowy.infrastructure.messaging

import org.springframework.context.event.EventListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.NeedUpdatedEvent
import ru.woowy.extension.classLogger
import ru.woowy.messaging.KafkaTopic

@Component
class NeedEventPublisher(
    kafkaTemplate: KafkaTemplate<String, Event>,
) : KafkaEventPublisher(kafkaTemplate) {
    private val logger = classLogger()

    @EventListener
    fun handleNeedUpdated(event: NeedUpdatedEvent) {
        logger.info("Handling need updated event: $event")
        sendCharacterStateEvent(event.characterId.toString(), event)
    }

    private fun sendCharacterStateEvent(
        key: String,
        event: Event,
    ) {
        sendEvent(KafkaTopic.CHARACTER_STATE_EVENTS, key, event)
    }
}