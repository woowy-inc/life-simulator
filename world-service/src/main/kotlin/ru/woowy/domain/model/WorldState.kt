package ru.woowy.domain.model

import kotlinx.coroutines.Job
import ru.woowy.game.GameConfig
import java.time.Instant

internal data class WorldState(
    val config: GameConfig,
    val startTime: Instant,
    var currentTime: Instant,
    var job: Job?,
    var isPaused: Boolean = false,
)