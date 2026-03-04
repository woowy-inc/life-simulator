package ru.woowy.domain.model

import java.util.UUID

typealias RegionNameCaseId = UUID

data class RegionNameCase(
    val id: RegionNameCaseId,
    val regionId: RegionId,
    override val nominative: String,
    override val genitive: String,
    override val dative: String,
    override val accusative: String,
    override val ablative: String,
    override val prepositional: String,
    override val locative: String,
) : NameCase()