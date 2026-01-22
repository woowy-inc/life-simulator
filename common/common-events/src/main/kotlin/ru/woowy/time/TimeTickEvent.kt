package ru.woowy.time

import java.time.Duration
import java.time.Instant

data class TimeTickEvent(
    val startedAt: Instant,
    val currentTime: Instant,
) {
    fun getTotalGameHours(): Long = Duration.between(startedAt, currentTime).toHours()
}
