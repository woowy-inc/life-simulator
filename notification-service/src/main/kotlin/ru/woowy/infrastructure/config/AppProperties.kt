package ru.woowy.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val frontendUrl: String,
    @NestedConfigurationProperty
    val mail: MailProperties,
) {
    data class MailProperties(
        val from: String,
        val fromName: String,
    )
}