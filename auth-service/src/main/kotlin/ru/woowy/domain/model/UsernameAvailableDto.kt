package ru.woowy.domain.model

import jakarta.validation.constraints.NotBlank

data class UsernameAvailableDto(
    @field:NotBlank("Username cannot be blank")
    val username: String,
    val isAvailable: Boolean,
)