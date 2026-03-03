package ru.woowy.infrastructure.persistence

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId
import ru.woowy.domain.repository.CityRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.persistence.entity.CityEntity
import ru.woowy.infrastructure.persistence.entity.RegionEntity
import ru.woowy.infrastructure.persistence.entity.TimezoneEntity
import ru.woowy.infrastructure.persistence.jpa.JpaCityRepository
import ru.woowy.infrastructure.persistence.jpa.JpaRegionRepository
import ru.woowy.infrastructure.persistence.jpa.JpaTimezoneRepository
import kotlin.jvm.optionals.getOrNull

@Repository
internal class CityRepositoryImpl(
    private val jpaCityRepository: JpaCityRepository,
    private val jpaRegionRepository: JpaRegionRepository,
    private val jpaTimezoneRepository: JpaTimezoneRepository,
) : CityRepository {
    override fun findAll(): List<City> = jpaCityRepository.findAll().map { it.asDomain() }

    override fun findById(cityId: CityId): City? = jpaCityRepository.findById(cityId).getOrNull()?.asDomain()

    override fun add(city: City): City = jpaCityRepository.save(city.asEntity(city)).asDomain()

    override fun update(city: City): City? = jpaCityRepository.save(city.asEntity(city)).asDomain()

    override fun delete(cityId: CityId) = jpaCityRepository.deleteById(cityId)

    private fun City.asEntity(city: City): CityEntity {
        val region = jpaRegionRepository.getReferenceById(city.region.id)
        val timezone = jpaTimezoneRepository.getReferenceById(city.timezone.timezoneId)

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