package ru.woowy.security

import java.util.UUID

data class UserDto(
    val id: UUID,
    val username: String,
    val email: String,
    val firstName: String,
    val role: UserRole,
)