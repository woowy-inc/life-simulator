package ru.woowy.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig(
    private val appProperties: AppProperties,
) {
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val origins =
            appProperties.cors.allowedOrigins
                .split(",")
                .map { it.trim() }

        val configuration =
            CorsConfiguration().apply {
                allowedOrigins = origins
                allowedMethods = listOf("*")
                allowedHeaders = listOf("*")
                exposedHeaders = listOf("*")
                allowCredentials = true
                maxAge = 3600L
            }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}