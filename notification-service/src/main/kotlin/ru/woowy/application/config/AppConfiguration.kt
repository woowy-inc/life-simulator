package ru.woowy.application.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(AppProperties::class)
class AppConfiguration {
    companion object {
        const val APP_VERSION = "0.1.0"
    }
}