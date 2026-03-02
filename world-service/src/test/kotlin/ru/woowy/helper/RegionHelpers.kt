package ru.woowy.helper

import ru.woowy.domain.model.Region
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase
import ru.woowy.util.randomInt
import ru.woowy.util.randomLong
import ru.woowy.util.randomShort
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID

internal fun randomRegion(
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
    yearFounded: Short = randomShort(1900, 2026),
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

internal fun randomRegionNameCase(
    regionId: RegionId = randomUUID(),
    nominative: String = randomString(),
    genitive: String = randomString(),
    dative: String = randomString(),
    accusative: String = randomString(),
    ablative: String = randomString(),
    prepositional: String = randomString(),
    locative: String = randomString(),
): RegionNameCase = RegionNameCase(
    regionId = regionId,
    nominative = nominative,
    genitive = genitive,
    dative = dative,
    accusative = accusative,
    ablative = ablative,
    prepositional = prepositional,
    locative = locative,
)