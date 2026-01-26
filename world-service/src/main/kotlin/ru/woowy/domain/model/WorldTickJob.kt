package ru.woowy.domain.model

import kotlinx.coroutines.Job
import ru.woowy.game.GameConfig
import java.time.Instant

internal data class WorldTickJob(
    val config: GameConfig,
    val startedAt: Instant,
    val process: Job,
)