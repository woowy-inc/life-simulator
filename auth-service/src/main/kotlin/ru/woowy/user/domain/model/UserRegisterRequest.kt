package ru.woowy.user.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "User register request")
internal data class UserRegisterRequest(
    @field:NotBlank("Username cannot be blank")
    val username: String,
    @field:Email("Email should be valid")
    val email: String,
    @field:NotBlank("Password cannot be blank")
    @field:Size("Password should be between 8 and 64 characters", min = 8, max = 64)
    val password: String,
    @field:NotBlank("First name cannot be blank")
    val firstName: String,
)