package ru.woowy.location.domain.model

import java.math.BigDecimal

internal data class Coordinate(
    val latitude: BigDecimal,
    val longitude: BigDecimal,
)