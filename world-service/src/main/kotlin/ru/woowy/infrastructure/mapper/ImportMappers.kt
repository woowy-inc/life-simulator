package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityImport
import ru.woowy.domain.model.Coordinate
import ru.woowy.domain.model.CoordinateImport
import ru.woowy.domain.model.Region
import ru.woowy.domain.model.RegionImport
import ru.woowy.domain.model.Timezone
import ru.woowy.domain.model.TimezoneImport

fun CityImport.asDomain() = City(
    id = this.id,
    region = this.region.asDomain(),
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
    coordinate = this.coordinate.asDomain(),
)

fun RegionImport.asDomain() = Region(
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

fun TimezoneImport.asDomain() = Timezone(
    timezoneId = this.tzid,
    abbreviation = this.abbreviation,
    utcOffset = this.utcOffset,
    mskOffset = this.mskOffset,
)

fun CoordinateImport.asDomain() = Coordinate(
    latitude = this.lat,
    longitude = this.lon,
)