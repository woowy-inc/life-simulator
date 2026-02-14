package ru.woowy.application.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
internal class CorsConfig(
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

        val source =
            UrlBasedCorsConfigurationSource().apply {
                registerCorsConfiguration("/**", configuration)
            }

        return source
    }

    @Bean
    fun corsFilter(): CorsFilter = CorsFilter(corsConfigurationSource())
}