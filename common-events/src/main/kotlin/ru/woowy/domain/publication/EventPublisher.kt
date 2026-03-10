package ru.woowy.domain.publication

import ru.woowy.domain.model.Event

interface EventPublisher {
    fun publish(event: Event)
}