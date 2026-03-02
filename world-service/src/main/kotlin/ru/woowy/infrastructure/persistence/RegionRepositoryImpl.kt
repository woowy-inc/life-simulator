package ru.woowy.infrastructure.persistence

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.Region
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.repository.RegionRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.mapper.asEntity
import ru.woowy.infrastructure.persistence.jpa.CrudRegionRepository
import kotlin.jvm.optionals.getOrNull

@Repository
internal class RegionRepositoryImpl(
    private val crudRepository: CrudRegionRepository,
) : RegionRepository {
    override fun findById(regionId: RegionId): Region? = crudRepository.findById(regionId).getOrNull()?.asDomain()

    override fun add(region: Region): Region = crudRepository.save(region.asEntity()).asDomain()

    override fun update(region: Region): Region? = crudRepository.save(region.asEntity()).asDomain()

    override fun delete(regionId: RegionId) = crudRepository.deleteById(regionId)
}