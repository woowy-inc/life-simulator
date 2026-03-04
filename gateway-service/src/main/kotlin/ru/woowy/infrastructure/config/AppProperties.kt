package ru.woowy.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties(prefix = "app")
data class AppProperties(
    @NestedConfigurationProperty
    val cors: CorsProperties,
) {
    data class CorsProperties(
        val allowedOrigins: String,
    )
}