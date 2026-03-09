package ru.woowy.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LocationImport(
    @field:JsonProperty("guid")
    val id: LocationId,
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
    @field:JsonProperty("yearCityStatus")
    val yearStatus: String,
    @field:JsonProperty("coords")
    val coordinate: CoordinateImport,
)