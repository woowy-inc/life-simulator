package ru.woowy.infrastructure.messaging

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import ru.woowy.application.util.serialize
import ru.woowy.domain.Event
import ru.woowy.game.KafkaTopic

@Component
internal class KafkaEventPublisherImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) : KafkaEventPublisher<String, String> {
    override fun publish(event: Event): SendResult<String, String> {
        val topic =
            when (event) {
                is Event.WorldTickEvent -> KafkaTopic.WORLD_TICK
            }

        return send(topic, event)
    }

    private fun send(
        topic: String,
        event: Event,
    ): SendResult<String, String> = kafkaTemplate.send(topic, event.worldId.toString(), event.serialize()).get()
}