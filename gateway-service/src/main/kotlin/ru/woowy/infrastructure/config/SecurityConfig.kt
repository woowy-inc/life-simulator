package ru.woowy.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.reactive.CorsConfigurationSource

@Configuration
class SecurityConfig {
    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        corsConfigurationSource: CorsConfigurationSource,
    ): SecurityWebFilterChain = http
        .cors { it.configurationSource(corsConfigurationSource) }
        .csrf { it.disable() }
        .authorizeExchange { it.anyExchange().permitAll() }
        .build()
}