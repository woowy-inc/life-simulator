package ru.woowy.game

private const val SECONDS_IN_HOUR = 3600.0

enum class GameSpeed(
    val gameSecondsPerTick: Long,
) {
    REAL_TIME(60),
    ACCELERATED(120),
    FAST(180),
}

fun GameSpeed.hoursElapsed(): Double = gameSecondsPerTick / SECONDS_IN_HOUR