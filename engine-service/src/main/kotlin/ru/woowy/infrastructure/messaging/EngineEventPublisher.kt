package ru.woowy.infrastructure.messaging

import org.springframework.context.event.EventListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.WorldTickEvent
import ru.woowy.messaging.KafkaTopic

@Component
class EngineEventPublisher(
    kafkaTemplate: KafkaTemplate<String, Event>,
) : KafkaEventPublisher(kafkaTemplate) {
    @EventListener
    fun handleWorldTick(event: WorldTickEvent) {
        sendEvent(KafkaTopic.WORLD_TICK_EVENTS, event.characterId.toString(), event)
    }
}