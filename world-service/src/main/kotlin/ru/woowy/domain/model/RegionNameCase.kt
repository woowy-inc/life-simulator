package ru.woowy.domain.model

internal data class RegionNameCase(
    val regionId: RegionId,
    override val nominative: String,
    override val genitive: String,
    override val dative: String,
    override val accusative: String,
    override val ablative: String,
    override val prepositional: String,
    override val locative: String,
) : NameCase()