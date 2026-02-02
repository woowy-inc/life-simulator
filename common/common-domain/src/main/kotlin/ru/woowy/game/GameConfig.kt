package ru.woowy.game

import ru.woowy.world.WorldTimeRate

data class GameConfig(
    val timeScale: WorldTimeRate = WorldTimeRate.NORMAL,
)