package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.CityNameCase
import ru.woowy.infrastructure.persistence.entity.CityEntity
import ru.woowy.infrastructure.persistence.entity.CityNameCaseEntity

internal fun CityNameCaseEntity.asDomain(): CityNameCase = CityNameCase(
    id = this.id,
    cityId = this.city.id,
    nominative = this.nominative,
    genitive = this.genitive,
    dative = this.dative,
    accusative = this.accusative,
    ablative = this.ablative,
    prepositional = this.prepositional,
    locative = this.locative,
)