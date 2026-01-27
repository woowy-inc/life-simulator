package ru.woowy.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.woowy.game.AppMode

@ConfigurationProperties(prefix = "app")
internal data class AppProperties(
    val mode: AppMode,
    val tickRate: Long,
)