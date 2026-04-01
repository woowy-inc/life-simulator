package ru.woowy.extension

import kotlinx.coroutines.Job

fun Job.invokeOnError(block: (Throwable) -> Unit) = invokeOnCompletion {
    if (it != null) block(it)
}