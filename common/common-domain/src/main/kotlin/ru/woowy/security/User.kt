package ru.woowy.security

import java.util.UUID

data class User(
    val id: UUID,
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val role: UserRole,
)