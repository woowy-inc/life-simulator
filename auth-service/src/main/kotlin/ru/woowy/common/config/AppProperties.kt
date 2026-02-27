package ru.woowy.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties(prefix = "app")
internal data class AppProperties(
    val cacheEnabled: Boolean,
    val serverUrl: String,
    @NestedConfigurationProperty
    val jwt: JwtProperties,
) {
    data class JwtProperties(
        val expiration: Long,
        val refreshExpiration: Long,
        val issuer: String,
        val keyId: String,
    )
}