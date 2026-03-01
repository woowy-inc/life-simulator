package ru.woowy.location.domain.model

import java.util.UUID

typealias CityId = UUID

internal data class City(
    val id: CityId,
    val region: Region,
    val timezone: Timezone,
    val okato: String,
    val oktmo: String,
    val label: String,
    val name: String,
    val nameAlt: String,
    val nameEn: String,
    val type: String,
    val typeShort: String,
    val contentType: String,
    val isDualName: Boolean,
    val isCapital: Boolean,
    val zip: Int,
    val population: Long,
    val yearFounded: Short,
    val yearCityStatus: Short,
    val coordinate: Coordinate,
    val nameCase: CityNameCase,
)