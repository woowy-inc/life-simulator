package ru.woowy.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RegionImport(
    @field:JsonProperty("guid")
    val id: RegionId,
    val okato: String,
    val oktmo: String,
    val code: String,
    @field:JsonProperty("iso_3166-2")
    val iso31662: String,
    val label: String,
    val name: String,
    @field:JsonProperty("name_en")
    val nameEn: String,
    @field:JsonProperty("fullname")
    val fullName: String,
    val unofficialName: String?,
    val type: String,
    val typeShort: String,
    val contentType: String,
    val population: Long,
    val yearFounded: String,
    val area: Int,
    val district: String,
)