package ru.woowy.domain.model

data class UsernameAvailableDto(
    val username: String,
    val isAvailable: Boolean,
)