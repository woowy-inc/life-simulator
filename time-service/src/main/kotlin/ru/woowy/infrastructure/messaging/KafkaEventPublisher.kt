package ru.woowy.infrastructure.messaging

import org.springframework.kafka.support.SendResult
import ru.woowy.domain.DomainEvent

interface KafkaEventPublisher<T : Any, E : Any> {
    fun publish(event: DomainEvent): SendResult<T, E>
}