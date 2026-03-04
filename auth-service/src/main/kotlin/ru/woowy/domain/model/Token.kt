package ru.woowy.domain.model

import jakarta.validation.constraints.NotBlank

data class Token(
    @field:NotBlank("Value cannot be blank")
    val value: String,
    val expiration: Long,
)