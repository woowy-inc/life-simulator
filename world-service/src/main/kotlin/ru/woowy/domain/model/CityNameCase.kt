package ru.woowy.domain.model

import java.util.UUID

typealias CityNameCaseId = UUID

data class CityNameCase(
    val id: CityNameCaseId,
    val cityId: CityId,
    override val nominative: String,
    override val genitive: String,
    override val dative: String,
    override val accusative: String,
    override val ablative: String,
    override val prepositional: String,
    override val locative: String,
) : NameCase()