package ru.woowy.domain.model

import kotlinx.serialization.Serializable
import ru.woowy.id.EventId

@Serializable
sealed interface Event {
    val eventId: EventId
    val timestamp: Long
}