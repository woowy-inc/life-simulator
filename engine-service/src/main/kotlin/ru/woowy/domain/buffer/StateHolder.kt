package ru.woowy.domain.buffer

interface StateHolder<K, V> {
    suspend fun get(key: K): V?

    suspend fun put(
        key: K,
        value: V,
    )

    suspend fun putIfAbsent(
        key: K,
        value: V,
    ): V?

    suspend fun remove(key: K): V?
}