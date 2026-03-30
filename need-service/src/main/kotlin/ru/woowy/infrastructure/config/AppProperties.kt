package ru.woowy.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val gatewayUrl: String,
    @NestedConfigurationProperty
    val decay: DecayProperties,
) {
    data class DecayProperties(
        val hunger: Double,
        val sleep: Double,
        val body: Double,
        val mental: Double,
        val social: Double,
    )
}