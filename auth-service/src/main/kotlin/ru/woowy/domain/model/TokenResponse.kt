package ru.woowy.domain.model

internal data class TokenResponse(
    val token: String,
    val expiresIn: Long,
)