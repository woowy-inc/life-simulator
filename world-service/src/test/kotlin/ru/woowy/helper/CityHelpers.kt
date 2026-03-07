package ru.woowy.helper

import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.Coordinate
import ru.woowy.domain.model.Region
import ru.woowy.domain.model.Timezone
import ru.woowy.util.randomBoolean
import ru.woowy.util.randomDouble
import ru.woowy.util.randomInt
import ru.woowy.util.randomLong
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID

fun randomCity(
    id: CityId = randomUUID(),
    region: Region = randomRegion(),
    timezone: Timezone = randomTimezone(),
    okato: String = randomString(),
    oktmo: String = randomString(),
    label: String = randomString(),
    name: String = randomString(),
    nameAlt: String = randomString(),
    nameEn: String = randomString(),
    type: String = randomString(),
    typeShort: String = randomString(),
    contentType: String = randomString(),
    isDualName: Boolean = randomBoolean(),
    isCapital: Boolean = randomBoolean(),
    zip: Int = randomInt(),
    population: Long = randomLong(),
    yearFounded: String = randomString(),
    yearCityStatus: String = randomString(),
    coordinate: Coordinate = randomCoordinate(),
): City = City(
    id = id,
    region = region,
    timezone = timezone,
    okato = okato,
    oktmo = oktmo,
    label = label,
    name = name,
    nameAlt = nameAlt,
    nameEn = nameEn,
    type = type,
    typeShort = typeShort,
    contentType = contentType,
    isDualName = isDualName,
    isCapital = isCapital,
    zip = zip,
    population = population,
    yearFounded = yearFounded,
    yearCityStatus = yearCityStatus,
    coordinate = coordinate,
)

fun randomCoordinate(
    latitude: Double = randomDouble(-90.0, 90.0),
    longitude: Double = randomDouble(-180.0, 180.0),
): Coordinate = Coordinate(
    latitude = latitude,
    longitude = longitude,
)