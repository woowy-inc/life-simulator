package ru.woowy.infrastructure.model

import ru.woowy.infrastructure.persistence.spec.LocationSpecs
import ru.woowy.infrastructure.persistence.spec.RegionSpecs

enum class LocationSortColumn(
    val column: String,
) {
    LOCATION(LocationSpecs.NAME),
    REGION(RegionSpecs.NAME),
    POPULATION(RegionSpecs.POPULATION),
}