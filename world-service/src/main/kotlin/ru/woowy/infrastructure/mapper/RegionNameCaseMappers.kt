package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.RegionNameCase
import ru.woowy.infrastructure.persistence.entity.RegionNameCaseEntity

internal fun RegionNameCaseEntity.asDomain(): RegionNameCase = RegionNameCase(
    regionId = this.regionId,
    nominative = this.nominative,
    genitive = this.genitive,
    dative = this.dative,
    accusative = this.accusative,
    ablative = this.ablative,
    prepositional = this.prepositional,
    locative = this.locative,
)

internal fun RegionNameCase.asEntity(): RegionNameCaseEntity = RegionNameCaseEntity(
    regionId = this.regionId,
    nominative = this.nominative,
    genitive = this.genitive,
    dative = this.dative,
    accusative = this.accusative,
    ablative = this.ablative,
    prepositional = this.prepositional,
    locative = this.locative,
)

internal fun List<RegionNameCaseEntity>.asDomainList(): List<RegionNameCase> = this.map { it.asDomain() }

internal fun List<RegionNameCase>.asEntityList(): List<RegionNameCaseEntity> = this.map { it.asEntity() }