package ru.woowy.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class LoadBalancerConfig {
    @Bean
    fun webClientBuilder(): WebClient.Builder = WebClient.builder()
}