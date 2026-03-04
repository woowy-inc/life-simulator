package ru.woowy.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema
data class TokenDto(
    @field:Schema(description = "Access token")
    @field:NotBlank("Access token cannot be blank")
    val accessToken: String,
    @field:Schema(description = "Access token duration in milliseconds")
    val accessTokenExpiresIn: Long,
    @field:Schema(description = "Refresh token")
    @field:NotBlank("Refresh token cannot be blank")
    val refreshToken: String,
    @field:Schema(description = "Refresh token duration in milliseconds")
    val refreshTokenExpiresIn: Long,
)