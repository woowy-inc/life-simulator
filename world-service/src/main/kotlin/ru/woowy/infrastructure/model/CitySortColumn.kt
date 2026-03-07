package ru.woowy.infrastructure.model

import ru.woowy.infrastructure.persistence.spec.CitySpecs
import ru.woowy.infrastructure.persistence.spec.RegionSpecs

enum class CitySortColumn(
    val column: String,
) {
    CITY(CitySpecs.NAME),
    REGION(RegionSpecs.NAME),
    POPULATION(RegionSpecs.POPULATION),
}