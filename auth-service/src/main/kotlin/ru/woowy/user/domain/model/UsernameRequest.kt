package ru.woowy.user.domain.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema
internal data class UsernameRequest(
    @field:Schema(
        description = "User nickname",
        example = "Player_1",
        nullable = false,
        required = true,
    )
    val username: String,
    @field:Schema(
        description = "User password",
        example = "P@ssword123!",
        format = "password",
        nullable = false,
        required = true,
    )
    val password: String,
)