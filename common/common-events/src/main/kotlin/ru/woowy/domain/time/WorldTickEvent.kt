package ru.woowy.domain.time

import ru.woowy.game.GameConfig
import java.time.Instant

data class WorldTickEvent(
    val config: GameConfig,
    val startedAt: Instant,
    val currentTime: Instant,
)