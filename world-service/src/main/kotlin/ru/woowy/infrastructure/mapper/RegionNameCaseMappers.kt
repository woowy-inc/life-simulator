package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.RegionNameCase
import ru.woowy.infrastructure.persistence.entity.RegionNameCaseEntity

fun RegionNameCaseEntity.asDomain(): RegionNameCase = RegionNameCase(
    id = this.id,
    regionId = this.region.id,
    nominative = this.nominative,
    genitive = this.genitive,
    dative = this.dative,
    accusative = this.accusative,
    ablative = this.ablative,
    prepositional = this.prepositional,
    locative = this.locative,
)