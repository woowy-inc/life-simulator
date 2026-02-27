package ru.woowy.user.domain.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User register request")
internal data class UserRegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
)