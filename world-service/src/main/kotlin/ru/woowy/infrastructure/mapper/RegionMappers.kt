package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.Region
import ru.woowy.infrastructure.persistence.entity.RegionEntity

fun RegionEntity.asDomain(): Region = Region(
    id = this.id,
    okato = this.okato,
    oktmo = this.oktmo,
    code = this.code,
    iso31662 = this.iso31662,
    label = this.label,
    name = this.name,
    nameEn = this.nameEn,
    fullName = this.fullName,
    unofficialName = this.unofficialName,
    type = this.type,
    typeShort = this.typeShort,
    contentType = this.contentType,
    population = this.population,
    yearFounded = this.yearFounded,
    area = this.area,
    district = this.district,
)