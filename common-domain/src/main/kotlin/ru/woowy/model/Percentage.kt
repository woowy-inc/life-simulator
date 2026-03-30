package ru.woowy.model

import java.math.BigDecimal

@JvmInline
value class Percentage private constructor(
    val value: BigDecimal,
) {
    companion object {
        private val MIN = BigDecimal(0)
        private val MAX = BigDecimal(100)

        fun of(value: BigDecimal): Percentage = Percentage(value.coerceIn(MIN, MAX))

        fun max(): Percentage = Percentage(MAX)

        fun min(): Percentage = Percentage(MIN)
    }

    operator fun plus(other: BigDecimal): Percentage = Percentage((value + other).coerceIn(MIN, MAX))

    operator fun plus(other: Int): Percentage = plus(other.toBigDecimal())

    operator fun plus(other: Long): Percentage = plus(other.toBigDecimal())

    operator fun plus(other: Double): Percentage = plus(other.toBigDecimal())

    operator fun plus(other: Percentage): Percentage = plus(other.value)

    operator fun minus(other: BigDecimal): Percentage = Percentage((value - other).coerceIn(MIN, MAX))

    operator fun minus(other: Int): Percentage = minus(other.toBigDecimal())

    operator fun minus(other: Long): Percentage = minus(other.toBigDecimal())

    operator fun minus(other: Double): Percentage = minus(other.toBigDecimal())

    operator fun minus(other: Percentage): Percentage = minus(other.value)

    operator fun times(other: BigDecimal): Percentage = Percentage((value * other).coerceIn(MIN, MAX))

    operator fun times(other: Int): Percentage = times(other.toBigDecimal())

    operator fun times(other: Long): Percentage = times(other.toBigDecimal())

    operator fun times(other: Double): Percentage = times(other.toBigDecimal())

    operator fun times(other: Percentage): Percentage = times(other.value)

    operator fun div(other: BigDecimal): Percentage = Percentage((value / other).coerceIn(MIN, MAX))

    operator fun div(other: Int): Percentage = div(other.toBigDecimal())

    operator fun div(other: Long): Percentage = div(other.toBigDecimal())

    operator fun div(other: Double): Percentage = div(other.toBigDecimal())

    operator fun div(other: Percentage): Percentage = div(other.value)

    fun toDouble(): Double = this.value.toDouble()
}