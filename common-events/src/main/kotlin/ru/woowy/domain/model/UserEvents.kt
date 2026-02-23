package ru.woowy.domain.model

import kotlinx.serialization.Serializable
import ru.woowy.security.UserRole

@Serializable
data class UserRegisterRequestedEvent(
    override val eventId: String,
    override val timestamp: Long,
    val userId: String,
    val username: String,
    val email: String,
    val firstName: String,
    val key: String,
) : Event

@Serializable
data class UserRegisteredEvent(
    override val eventId: String,
    override val timestamp: Long,
    val userId: String,
    val username: String,
    val email: String,
    val firstName: String,
    val role: UserRole,
) : Event