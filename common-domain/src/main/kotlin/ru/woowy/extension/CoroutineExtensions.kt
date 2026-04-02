package ru.woowy.extension

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import org.slf4j.Logger

fun Job.invokeOnFailure(block: (Throwable) -> Unit) = invokeOnCompletion {
    if (it != null) block(it)
}

fun <T> CoroutineScope.asyncCatching(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<Result<T>> {
    return async(context, start) { runCatching { block() } }
}

fun <T> Result<T>.getOrNullLogging(logger: Logger? = null): T? {
    val ex = exceptionOrNull()

    ex?.let {
        if (logger == null) {
            println(this.isFailure)
        } else {
            logger.error(it.message)
        }
    }

    return getOrNull()
}