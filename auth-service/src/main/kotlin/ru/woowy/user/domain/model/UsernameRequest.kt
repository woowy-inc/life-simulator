package ru.woowy.user.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema
internal data class UsernameRequest(
    @field:Schema(
        description = "User nickname",
        example = "Player_1",
        nullable = false,
        required = true,
    )
    @field:NotBlank("Username cannot be blank")
    val username: String,
    @field:Schema(
        description = "User password",
        example = "P@ssword123!",
        format = "password",
        nullable = false,
        required = true,
    )
    @field:NotBlank("Username cannot be blank")
    @field:Size("Password should be between 8 and 64 characters", min = 8, max = 64)
    val password: String,
)