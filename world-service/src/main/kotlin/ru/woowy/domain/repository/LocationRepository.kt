package ru.woowy.domain.repository

import org.springframework.data.domain.Sort
import ru.woowy.domain.model.Location
import ru.woowy.domain.model.LocationId
import ru.woowy.domain.model.LocationPageable
import ru.woowy.infrastructure.model.LocationSortColumn

interface LocationRepository {
    fun findAll(
        sortColumn: LocationSortColumn,
        sortOrder: Sort.Direction,
        search: String?,
        page: Int?,
        count: Int?,
    ): LocationPageable

    fun findAll(): LocationPageable

    fun findById(locationId: LocationId): Location?

    fun add(location: Location): Location

    fun addOrUpdate(cities: List<Location>): List<Location>

    fun update(location: Location): Location?

    fun delete(locationId: LocationId)

    fun isEmpty(): Boolean
}