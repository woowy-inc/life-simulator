package ru.woowy.helper

import ru.woowy.domain.model.Region
import ru.woowy.domain.model.RegionId
import ru.woowy.util.randomInt
import ru.woowy.util.randomLong
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID

fun randomRegion(
    id: RegionId = randomUUID(),
    okato: String = randomString(),
    oktmo: String = randomString(),
    code: String = randomString(),
    iso31662: String = randomString(),
    label: String = randomString(),
    name: String = randomString(),
    nameEn: String = randomString(),
    fullName: String = randomString(),
    unofficialName: String? = randomString(),
    type: String = randomString(),
    typeShort: String = randomString(),
    contentType: String = randomString(),
    population: Long = randomLong(),
    yearFounded: String = randomString(),
    area: Int = randomInt(),
    district: String = randomString(),
): Region = Region(
    id = id,
    okato = okato,
    oktmo = oktmo,
    code = code,
    iso31662 = iso31662,
    label = label,
    name = name,
    nameEn = nameEn,
    fullName = fullName,
    unofficialName = unofficialName,
    type = type,
    typeShort = typeShort,
    contentType = contentType,
    population = population,
    yearFounded = yearFounded,
    area = area,
    district = district,
)