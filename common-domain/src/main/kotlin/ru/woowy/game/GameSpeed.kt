package ru.woowy.game

enum class GameSpeed(
    val gameMinutesPerTick: Int,
) {
    REAL_TIME(gameMinutesPerTick = 3),
    ACCELERATED(gameMinutesPerTick = 72),
    FAST(gameMinutesPerTick = 504),
}