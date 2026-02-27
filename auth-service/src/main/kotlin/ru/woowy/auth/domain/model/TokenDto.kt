package ru.woowy.auth.domain.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema
internal data class TokenDto(
    @field:Schema(description = "Access token")
    val accessToken: String,
    @field:Schema(description = "Access token duration in milliseconds")
    val accessTokenExpiresIn: Long,
    @field:Schema(description = "Refresh token")
    val refreshToken: String,
    @field:Schema(description = "Refresh token duration in milliseconds")
    val refreshTokenExpiresIn: Long,
)