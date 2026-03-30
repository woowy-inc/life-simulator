package ru.woowy.infrastructure.util

import org.springframework.data.redis.core.ListOperations

fun <K : Any, V : Any> ListOperations<K, V>.getAll(key: K): List<V> = range(key, 0, -1) ?: emptyList()