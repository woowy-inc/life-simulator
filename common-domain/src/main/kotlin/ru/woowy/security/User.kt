package ru.woowy.security

import ru.woowy.id.UserId

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val role: UserRole,
    val isEmailVerified: Boolean,
)