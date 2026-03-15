package ru.woowy.game

enum class GameSpeed(
    val gameMinutesPerTick: Long,
) {
    REAL_TIME(10),
    ACCELERATED(60),
    FAST(120),
}