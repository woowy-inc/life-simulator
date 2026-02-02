package ru.woowy.application.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.jwt")
internal data class JwtProperties(
    val expiration: Long,
    val issuer: String,
    val keyId: String,
)