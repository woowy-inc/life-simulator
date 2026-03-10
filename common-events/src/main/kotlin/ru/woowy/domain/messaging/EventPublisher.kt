package ru.woowy.domain.messaging

import ru.woowy.domain.model.Event

interface EventPublisher {
    fun publish(event: Event)
}