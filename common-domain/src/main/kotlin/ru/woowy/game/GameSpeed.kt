package ru.woowy.game

private const val MINUTES_IN_HOUR = 60.0

enum class GameSpeed(
    val gameMinutesPerTick: Long,
) {
    REAL_TIME(1),
    ACCELERATED(5),
    FAST(10),
}

fun GameSpeed.hoursElapsed(): Double = gameMinutesPerTick / MINUTES_IN_HOUR