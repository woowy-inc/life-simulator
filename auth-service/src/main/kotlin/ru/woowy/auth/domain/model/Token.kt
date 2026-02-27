package ru.woowy.auth.domain.model

import jakarta.validation.constraints.NotBlank

internal data class Token(
    @field:NotBlank("Value cannot be blank")
    val value: String,
    val expiration: Long,
)