package ru.woowy.application.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties(prefix = "app")
internal data class AppProperties(
    val tickRate: Long,
    @NestedConfigurationProperty
    val jwt: JwtProperties = JwtProperties(),
) {
    internal data class JwtProperties(
        val issuer: String = "",
    )
}