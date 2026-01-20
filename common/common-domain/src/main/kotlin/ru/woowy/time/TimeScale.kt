package ru.woowy.time

enum class TimeScale(
    val gameHoursPerRealSecond: Int,
) {
    SLOW(1),
    NORMAL(24),
    FAST(168),
}
