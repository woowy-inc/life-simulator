package ru.woowy.domain.usecase

import org.springframework.data.domain.Sort
import ru.woowy.domain.model.Importable
import ru.woowy.domain.model.Location
import ru.woowy.domain.model.LocationId
import ru.woowy.domain.model.LocationPageable
import ru.woowy.infrastructure.model.LocationSortColumn

interface LocationUseCase : Importable<Location> {
    fun getAll(
        sortColumn: LocationSortColumn,
        sortOrder: Sort.Direction,
        search: String?,
        page: Int?,
        count: Int?,
    ): LocationPageable

    fun get(locationId: LocationId): Location?

    fun add(location: Location): Location

    fun update(location: Location): Location?

    fun delete(locationId: LocationId)

    fun isEmpty(): Boolean
}