package ru.woowy.world

import java.time.Instant

data class WorldTime(
    val startedAt: Instant,
    val currentTime: Instant,
)