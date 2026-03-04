package ru.woowy.domain.model

import java.util.UUID

typealias RegionId = UUID

data class Region(
    val id: RegionId,
    val okato: String,
    val oktmo: String,
    val code: String,
    val iso31662: String,
    val label: String,
    val name: String,
    val nameEn: String,
    val fullName: String,
    val unofficialName: String?,
    val type: String,
    val typeShort: String,
    val contentType: String,
    val population: Long,
    val yearFounded: Short,
    val area: Int,
    val district: String,
    val nameCase: RegionNameCase?,
)