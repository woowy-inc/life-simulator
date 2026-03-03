package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.City
import ru.woowy.domain.model.Coordinate
import ru.woowy.infrastructure.persistence.entity.CityEntity
import ru.woowy.infrastructure.persistence.entity.RegionEntity

internal fun CityEntity.asDomain(): City = City(
    id = this.id,
    region = this.region.asDomain(),
    nameCase = this.nameCase?.asDomain(),
    timezone = this.timezone.asDomain(),
    okato = this.okato,
    oktmo = this.oktmo,
    label = this.label,
    name = this.name,
    nameAlt = this.nameAlt,
    nameEn = this.nameEn,
    type = this.type,
    typeShort = this.typeShort,
    contentType = this.contentType,
    isDualName = this.isDualName,
    isCapital = this.isCapital,
    zip = this.zip,
    population = this.population,
    yearFounded = this.yearFounded,
    yearCityStatus = this.yearCityStatus,
    coordinate = Coordinate(latitude = this.latitude, longitude = this.longitude),
)