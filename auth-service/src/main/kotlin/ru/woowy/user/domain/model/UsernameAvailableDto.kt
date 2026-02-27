package ru.woowy.user.domain.model

data class UsernameAvailableDto(
    val username: String,
    val isAvailable: Boolean,
)