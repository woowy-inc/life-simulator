package ru.woowy.model

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Percentage private constructor(
    val value: BigDecimal,
) {
    companion object {
        private const val SCALE = 2
        private val MIN = BigDecimal(0)
        private val MAX = BigDecimal(100)

        fun of(value: BigDecimal): Percentage =
            Percentage(value.setScale(SCALE, RoundingMode.HALF_UP).coerceIn(MIN, MAX))

        fun max(): Percentage = Percentage(MAX)

        fun min(): Percentage = Percentage(MIN)
    }

    operator fun plus(other: BigDecimal) = of((value + other))

    operator fun plus(other: Int) = plus(other.toBigDecimal())

    operator fun plus(other: Long) = plus(other.toBigDecimal())

    operator fun plus(other: Double) = plus(other.toBigDecimal())

    operator fun plus(other: Percentage) = plus(other.value)

    operator fun minus(other: BigDecimal) = of((value - other))

    operator fun minus(other: Int): Percentage = minus(other.toBigDecimal())

    operator fun minus(other: Long) = minus(other.toBigDecimal())

    operator fun minus(other: Double) = minus(other.toBigDecimal())

    operator fun minus(other: Percentage) = minus(other.value)

    operator fun times(other: BigDecimal) = of((value * other))

    operator fun times(other: Int) = times(other.toBigDecimal())

    operator fun times(other: Long) = times(other.toBigDecimal())

    operator fun times(other: Double) = times(other.toBigDecimal())

    operator fun times(other: Percentage) = times(other.value)

    operator fun div(other: BigDecimal) = of((value / other))

    operator fun div(other: Int) = div(other.toBigDecimal())

    operator fun div(other: Long) = div(other.toBigDecimal())

    operator fun div(other: Double) = div(other.toBigDecimal())

    operator fun div(other: Percentage) = div(other.value)

    fun toDouble(): Double = this.value.toDouble()
}