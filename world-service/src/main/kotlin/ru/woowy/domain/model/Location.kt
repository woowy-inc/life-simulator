package ru.woowy.domain.model

import java.util.UUID

typealias LocationId = UUID

data class Location(
    val id: LocationId,
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
    val yearFounded: String,
    val yearStatus: String,
    val coordinate: Coordinate,
)