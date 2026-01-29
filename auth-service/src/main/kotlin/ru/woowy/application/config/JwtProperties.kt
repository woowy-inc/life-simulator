package ru.woowy.application.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.jwt")
internal data class JwtProperties(
    val secret: String,
    val expiration: Long,
)