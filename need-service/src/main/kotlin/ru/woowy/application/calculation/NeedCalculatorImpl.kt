package ru.woowy.application.calculation

import org.springframework.stereotype.Component
import ru.woowy.domain.calculation.NeedCalculator
import ru.woowy.domain.model.Need
import ru.woowy.game.GameSpeed
import ru.woowy.game.hoursElapsed
import ru.woowy.infrastructure.config.AppProperties
import ru.woowy.model.Percentage
import java.math.BigDecimal
import java.time.OffsetDateTime

@Component
class NeedCalculatorImpl(
    appProperties: AppProperties,
) : NeedCalculator {
    private val decay = appProperties.decay

    override fun applyModifier(
        need: Need,
        gameSpeed: GameSpeed,
    ): Need {
        val hunger = applyModifier(need.hunger.value, decay.hunger, gameSpeed)
        val sleep = applyModifier(need.sleep.value, decay.sleep, gameSpeed)
        val body = applyModifier(need.body.value, decay.body, gameSpeed)
        val mental = applyModifier(need.mental.value, decay.mental, gameSpeed)
        val social = applyModifier(need.social.value, decay.social, gameSpeed)
        val healthConsumers = listOf(hunger, sleep, body)
        val happinessConsumers = listOf(mental, social)

        return need.copy(
            hunger = hunger,
            sleep = sleep,
            body = body,
            mental = mental,
            social = social,
            health =
                Percentage
                    .of(healthConsumers.sumOf { it.value } / healthConsumers.size.toBigDecimal()),
            happiness =
                Percentage
                    .of(happinessConsumers.sumOf { it.value } / happinessConsumers.size.toBigDecimal()),
            createdAt = OffsetDateTime.now(),
        )
    }

    override fun applyModifier(
        current: BigDecimal,
        modifier: Double,
        gameSpeed: GameSpeed,
    ): Percentage = Percentage.of(current - modifier.toBigDecimal() * gameSpeed.hoursElapsed().toBigDecimal())
}