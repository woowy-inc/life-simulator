package ru.woowy.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Coordinate(
    @field:JsonProperty("lat")
    val latitude: Double,
    @field:JsonProperty("lon")
    val longitude: Double,
)