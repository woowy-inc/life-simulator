package ru.woowy.infrastructure.flush

import kotlin.time.Instant

data class FlushContext(
    val timesCount: Long,
    val recordCount: Long,
    val flushedAt: Instant,
)