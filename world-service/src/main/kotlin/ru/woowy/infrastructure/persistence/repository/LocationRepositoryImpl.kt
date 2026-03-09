package ru.woowy.infrastructure.persistence.repository

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import ru.woowy.domain.model.Location
import ru.woowy.domain.model.LocationId
import ru.woowy.domain.model.LocationPageable
import ru.woowy.domain.repository.LocationRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.model.LocationSortColumn
import ru.woowy.infrastructure.persistence.entity.LocationEntity
import ru.woowy.infrastructure.persistence.entity.RegionEntity
import ru.woowy.infrastructure.persistence.entity.TimezoneEntity
import ru.woowy.infrastructure.persistence.jpa.LocationJpaRepository
import ru.woowy.infrastructure.persistence.jpa.RegionJpaRepository
import ru.woowy.infrastructure.persistence.jpa.TimezoneJpaRepository
import ru.woowy.infrastructure.persistence.spec.LocationSpecs
import kotlin.jvm.optionals.getOrNull

@Repository
class LocationRepositoryImpl(
    private val locationJpaRepository: LocationJpaRepository,
    private val regionJpaRepository: RegionJpaRepository,
    private val timezoneJpaRepository: TimezoneJpaRepository,
) : LocationRepository {
    override fun findAll(
        sortColumn: LocationSortColumn,
        sortOrder: Sort.Direction,
        search: String?,
        page: Int?,
        count: Int?,
    ): LocationPageable {
        val pageable = PageRequest.of(page ?: 0, count ?: 30)
        val spec = LocationSpecs.bySearch(search).and(LocationSpecs.bySort(sortColumn, sortOrder))
        val page = locationJpaRepository.findAll(spec, pageable).map { it.asDomain() }

        return LocationPageable(
            page = page.number,
            totalPages = page.totalPages,
            totalRecords = page.totalElements,
            data = page.content,
        )
    }

    override fun findAll(): LocationPageable = findAll(
        sortColumn = LocationSortColumn.POPULATION,
        sortOrder = Sort.Direction.DESC,
        search = null,
        page = null,
        count = null,
    )

    override fun findById(locationId: LocationId): Location? =
        locationJpaRepository.findById(locationId).getOrNull()?.asDomain()

    override fun add(location: Location): Location = locationJpaRepository.save(location.asEntity()).asDomain()

    override fun addOrUpdate(cities: List<Location>): List<Location> = locationJpaRepository
        .saveAll(cities.map { it.asEntity() })
        .map { it.asDomain() }

    override fun update(location: Location): Location? = locationJpaRepository.save(location.asEntity()).asDomain()

    override fun delete(locationId: LocationId) = locationJpaRepository.deleteById(locationId)

    override fun isEmpty(): Boolean = !locationJpaRepository.existsBy()

    private fun Location.asEntity(): LocationEntity {
        val region = regionJpaRepository.getReferenceById(this.region.id)
        val timezone = timezoneJpaRepository.getReferenceById(this.timezone.timezoneId)

        return asEntity(region, timezone)
    }

    private fun Location.asEntity(
        region: RegionEntity,
        timezone: TimezoneEntity,
    ): LocationEntity = LocationEntity(
        id = this.id,
        region = region,
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
        yearStatus = this.yearStatus,
        timezone = timezone,
        latitude = this.coordinate.latitude,
        longitude = this.coordinate.longitude,
    )
}