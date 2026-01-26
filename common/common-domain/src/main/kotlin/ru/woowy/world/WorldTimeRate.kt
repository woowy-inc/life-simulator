package ru.woowy.world

enum class WorldTimeRate(
    val gameHoursPerRealSecond: Int,
) {
    SLOW(1),
    NORMAL(2),
    FAST(24),
}