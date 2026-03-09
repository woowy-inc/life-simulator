package ru.woowy.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.Region
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.repository.RegionRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.persistence.entity.RegionEntity
import ru.woowy.infrastructure.persistence.jpa.RegionJpaRepository
import kotlin.jvm.optionals.getOrNull

@Repository
class RegionRepositoryImpl(
    private val regionJpaRepository: RegionJpaRepository,
) : RegionRepository {
    override fun findById(regionId: RegionId): Region? = regionJpaRepository.findById(regionId).getOrNull()?.asDomain()

    override fun add(region: Region): Region = regionJpaRepository.save(region.asEntity()).asDomain()

    override fun addOrUpdate(regions: List<Region>): List<Region> = regionJpaRepository
        .saveAll(regions.map { it.asEntity() })
        .map { it.asDomain() }

    override fun update(region: Region): Region? = regionJpaRepository.save(region.asEntity()).asDomain()

    override fun delete(regionId: RegionId) = regionJpaRepository.deleteById(regionId)

    private fun Region.asEntity(): RegionEntity = RegionEntity(
        id = this.id,
        okato = this.okato,
        oktmo = this.oktmo,
        code = this.code,
        iso31662 = this.iso31662,
        label = this.label,
        name = this.name,
        nameEn = this.nameEn,
        fullName = this.fullName,
        unofficialName = this.unofficialName,
        type = this.type,
        typeShort = this.typeShort,
        contentType = this.contentType,
        population = this.population,
        yearFounded = this.yearFounded,
        area = this.area,
        district = this.district,
    )
}