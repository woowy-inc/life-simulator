package ru.woowy.application.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
internal class RestClientConfig {
    @Bean
    fun restClient(): RestClient = RestClient.create()
}