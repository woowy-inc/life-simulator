package ru.woowy.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserEvent(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val role: String,
)

@Serializable
data class UserRegisteredEvent(
    override val eventId: String,
    override val timestamp: Long,
    val user: UserEvent,
) : Event