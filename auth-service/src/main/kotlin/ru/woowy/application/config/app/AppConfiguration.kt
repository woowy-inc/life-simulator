package ru.woowy.application.config.app

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(AppProperties::class)
internal class AppConfiguration {
    companion object {
        const val APP_VERSION = "0.28.4"
    }
}