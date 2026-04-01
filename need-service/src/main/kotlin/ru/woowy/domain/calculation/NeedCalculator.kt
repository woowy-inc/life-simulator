package ru.woowy.domain.calculation

import ru.woowy.domain.model.Need
import ru.woowy.game.GameSpeed
import ru.woowy.model.Percentage
import java.math.BigDecimal

interface NeedCalculator {
    fun applyModifier(
        need: Need,
        gameSpeed: GameSpeed,
    ): Need

    fun applyModifier(
        current: BigDecimal,
        modifier: Double,
        gameSpeed: GameSpeed,
    ): Percentage
}