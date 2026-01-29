package ru.woowy.infrastructure.messaging

import org.springframework.kafka.support.SendResult
import ru.woowy.domain.Event

interface KafkaEventPublisher<T : Any, E : Any> {
    fun publish(event: Event): SendResult<T, E>
}