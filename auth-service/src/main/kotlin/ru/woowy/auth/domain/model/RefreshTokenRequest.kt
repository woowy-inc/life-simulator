package ru.woowy.auth.domain.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema
internal data class RefreshTokenRequest(
    @field:Schema(
        description = "refresh token",
        nullable = false,
        required = true,
    )
    val refreshToken: String,
)