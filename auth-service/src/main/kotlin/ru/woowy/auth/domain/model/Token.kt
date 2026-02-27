package ru.woowy.auth.domain.model

internal data class Token(
    val value: String,
    val expiration: Long,
)