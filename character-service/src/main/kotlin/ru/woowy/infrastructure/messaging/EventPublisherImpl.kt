package ru.woowy.infrastructure.messaging

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import ru.woowy.domain.model.Event
import ru.woowy.domain.publication.EventPublisher

@Component
class EventPublisherImpl(
    private val applicationEventPublisher: ApplicationEventPublisher,
) : EventPublisher {
    override fun publish(event: Event) = applicationEventPublisher.publishEvent(event)
}