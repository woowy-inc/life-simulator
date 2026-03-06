package ru.woowy.infrastructure.persistence.repository

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.CityPageable
import ru.woowy.domain.repository.CityRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.model.CitySortColumn
import ru.woowy.infrastructure.persistence.entity.CityEntity
import ru.woowy.infrastructure.persistence.entity.RegionEntity
import ru.woowy.infrastructure.persistence.entity.TimezoneEntity
import ru.woowy.infrastructure.persistence.jpa.JpaCityRepository
import ru.woowy.infrastructure.persistence.jpa.JpaRegionRepository
import ru.woowy.infrastructure.persistence.jpa.JpaTimezoneRepository
import ru.woowy.infrastructure.persistence.spec.CitySpecs
import kotlin.jvm.optionals.getOrNull

@Repository
class CityRepositoryImpl(
    private val jpaCityRepository: JpaCityRepository,
    private val jpaRegionRepository: JpaRegionRepository,
    private val jpaTimezoneRepository: JpaTimezoneRepository,
) : CityRepository {
    override fun findAll(
        sortColumn: CitySortColumn,
        sortOrder: Sort.Direction,
        search: String?,
        page: Int?,
        count: Int?,
    ): CityPageable {
        val pageable = PageRequest.of(page ?: 0, count ?: 30)
        val spec = CitySpecs.bySearch(search).and(CitySpecs.bySort(sortColumn, sortOrder))
        val page = jpaCityRepository.findAll(spec, pageable).map { it.asDomain() }

        return CityPageable(
            page = page.number,
            totalPages = page.totalPages,
            totalRecords = page.totalElements,
            data = page.content,
        )
    }

    override fun findAll(): CityPageable = findAll(
        sortColumn = CitySortColumn.POPULATION,
        sortOrder = Sort.Direction.DESC,
        search = null,
        page = null,
        count = null,
    )

    override fun findById(cityId: CityId): City? = jpaCityRepository.findById(cityId).getOrNull()?.asDomain()

    override fun add(city: City): City = jpaCityRepository.save(city.asEntity()).asDomain()

    override fun addOrUpdate(cities: List<City>): List<City> = jpaCityRepository
        .saveAll(cities.map { it.asEntity() })
        .map { it.asDomain() }

    override fun update(city: City): City? = jpaCityRepository.save(city.asEntity()).asDomain()

    override fun delete(cityId: CityId) = jpaCityRepository.deleteById(cityId)

    override fun isEmpty(): Boolean = !jpaCityRepository.existsBy()

    private fun City.asEntity(): CityEntity {
        val region = jpaRegionRepository.getReferenceById(this.region.id)
        val timezone = jpaTimezoneRepository.getReferenceById(this.timezone.timezoneId)

        return asEntity(region, timezone)
    }

    private fun City.asEntity(
        region: RegionEntity,
        timezone: TimezoneEntity,
    ): CityEntity = CityEntity(
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
        yearCityStatus = this.yearCityStatus,
        timezone = timezone,
        latitude = this.coordinate.latitude,
        longitude = this.coordinate.longitude,
    )
}