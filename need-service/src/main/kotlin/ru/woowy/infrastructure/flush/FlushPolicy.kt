package ru.woowy.infrastructure.flush

import kotlin.time.Clock
import kotlin.time.Duration

fun interface FlushPolicy {
    fun shouldFlush(context: FlushContext): Boolean

    companion object {
        fun everyNTimes(n: Long) = FlushPolicy { it.timesCount % n == 0L }

        fun everyNRecords(n: Long) = FlushPolicy { it.recordCount >= n }

        fun everyDuration(duration: Duration) = FlushPolicy {
            Clock.System.now() - it.flushedAt >= duration
        }
    }
}