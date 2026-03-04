package ru.woowy.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema
data class RefreshTokenRequest(
    @field:Schema(
        description = "refresh token",
        nullable = false,
        required = true,
    )
    @field:NotBlank("Refresh token cannot be blank")
    val refreshToken: String,
)