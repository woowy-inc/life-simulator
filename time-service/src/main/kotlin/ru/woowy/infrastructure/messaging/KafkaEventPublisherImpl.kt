package ru.woowy.infrastructure.messaging

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import ru.woowy.domain.DomainEvent
import ru.woowy.game.KafkaTopic
import ru.woowy.infrastructure.util.serialize

@Component
class KafkaEventPublisherImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) : KafkaEventPublisher<String, String> {
    override fun publish(event: DomainEvent): SendResult<String, String> = when (event) {
        is DomainEvent.WorldTickEvent -> send(KafkaTopic.WORLD_TICK, event)
    }

    private fun send(
        topic: KafkaTopic,
        event: DomainEvent,
    ): SendResult<String, String> = kafkaTemplate.send(topic.title, event.id.toString(), event.serialize()).get()
}