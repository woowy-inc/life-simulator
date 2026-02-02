package ru.woowy.domain.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema
internal data class TokenResponse(
    @field:Schema(description = "Access token")
    val token: String,
    @field:Schema(description = "Token duration in milliseconds")
    val expiresIn: Long,
)