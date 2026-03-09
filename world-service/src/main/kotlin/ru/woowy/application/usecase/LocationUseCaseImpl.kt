package ru.woowy.application.usecase

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.woowy.domain.model.Location
import ru.woowy.domain.model.LocationId
import ru.woowy.domain.model.LocationPageable
import ru.woowy.domain.repository.LocationRepository
import ru.woowy.domain.usecase.LocationUseCase
import ru.woowy.infrastructure.model.LocationSortColumn

@Service
class LocationUseCaseImpl(
    private val locationRepository: LocationRepository,
) : LocationUseCase {
    override fun getAll(
        sortColumn: LocationSortColumn,
        sortOrder: Sort.Direction,
        search: String?,
        page: Int?,
        count: Int?,
    ): LocationPageable = locationRepository.findAll(
        sortColumn = sortColumn,
        sortOrder = sortOrder,
        search = search,
        page = page,
        count = count,
    )

    override fun get(locationId: LocationId): Location? = locationRepository.findById(locationId)

    override fun add(location: Location): Location = locationRepository.add(location)

    override fun update(location: Location): Location? = locationRepository.update(location)

    override fun delete(locationId: LocationId) = locationRepository.delete(locationId)

    override fun isEmpty(): Boolean = locationRepository.isEmpty()

    override fun import(data: List<Location>): List<Location> = locationRepository.addOrUpdate(data)
}