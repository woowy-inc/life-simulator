package ru.woowy.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Event {
    val eventId: String
    val timestamp: Long
}