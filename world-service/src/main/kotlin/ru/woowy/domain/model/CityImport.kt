package ru.woowy.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CityImport(
    @field:JsonProperty("guid")
    val id: CityId,
    val region: RegionImport,
    val timezone: TimezoneImport,
    val okato: String,
    val oktmo: String,
    val label: String,
    val name: String,
    @field:JsonProperty("name_alt")
    val nameAlt: String,
    @field:JsonProperty("name_en")
    val nameEn: String,
    val type: String,
    val typeShort: String,
    val contentType: String,
    val isDualName: Boolean,
    val isCapital: Boolean,
    val zip: Int,
    val population: Long,
    val yearFounded: String,
    val yearCityStatus: String,
    @field:JsonProperty("coords")
    val coordinate: CoordinateImport,
)