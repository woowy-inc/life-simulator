package ru.woowy.application.config

import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
internal class AuthenticationScheme {
    companion object {
        const val BEARER = "Bearer"
    }

    @Bean
    fun bearerScheme(): SecurityScheme = SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme(BEARER)
        .bearerFormat("JWT")
        .`in`(SecurityScheme.In.HEADER)
        .name("Authorization")
}