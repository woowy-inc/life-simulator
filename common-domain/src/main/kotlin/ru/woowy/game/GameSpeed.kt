package ru.woowy.game

enum class GameSpeed(
    val gameMinutesPerTick: Int,
) {
    REAL_TIME(gameMinutesPerTick = GameConfig.TICK_INTERVAL_SECONDS),
    ACCELERATED(gameMinutesPerTick = 72),
    FAST(gameMinutesPerTick = 504),
}