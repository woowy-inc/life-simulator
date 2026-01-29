package ru.woowy.domain.model

import ru.woowy.game.GameConfig
import java.time.Duration
import java.time.Instant

internal data class SessionState(
    val config: GameConfig,
    val startTime: Instant,
    val pausedAt: Instant? = null,
    val accumulatedTime: Duration = Duration.ZERO,
) {
    fun getCurrentTime(now: Instant = Instant.now()): Instant {
        val elapsed =
            if (pausedAt != null) {
                accumulatedTime
            } else {
                accumulatedTime + Duration.between(startTime, now)
            }

        return startTime.plus(elapsed)
    }

    val isPaused: Boolean get() = pausedAt != null
}