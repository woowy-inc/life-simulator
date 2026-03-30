package ru.woowy.infrastructure.flush

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

abstract class Flushable(
    private val policy: FlushPolicy = FlushPolicy.everyDuration(1.minutes),
) {
    private val mutex = Mutex()
    private var context =
        FlushContext(
            timesCount = 0L,
            recordCount = 0L,
            flushedAt = Clock.System.now(),
        )

    suspend fun notifyFlush(recordCount: Long = 0L) = mutex.withLock {
        context =
            context.copy(
                timesCount = context.timesCount + 1,
                recordCount = recordCount,
            )

        if (policy.shouldFlush(context)) {
            flush()

            context =
                FlushContext(
                    timesCount = 0L,
                    recordCount = 0L,
                    flushedAt = Clock.System.now(),
                )
        }
    }

    abstract suspend fun flush()
}