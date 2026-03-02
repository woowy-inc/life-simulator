package ru.woowy.infrastructure.persistence

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase
import ru.woowy.domain.repository.RegionNameCaseRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.mapper.asDomainList
import ru.woowy.infrastructure.mapper.asEntity
import ru.woowy.infrastructure.mapper.asEntityList
import ru.woowy.infrastructure.persistence.jpa.CrudRegionNameCaseRepository

@Repository
internal class RegionNameCaseRepositoryImpl(
    private val crudRepository: CrudRegionNameCaseRepository,
) : RegionNameCaseRepository {
    override fun findAllByRegionId(regionId: RegionId): List<RegionNameCase> =
        crudRepository.findAllByRegionId(regionId).asDomainList()

    override fun add(nameCase: RegionNameCase): RegionNameCase = crudRepository.save(nameCase.asEntity()).asDomain()

    override fun add(nameCases: List<RegionNameCase>): List<RegionNameCase> =
        crudRepository.saveAll(nameCases.asEntityList()).toList().asDomainList()

    override fun deleteAll(regionId: RegionId) = crudRepository.deleteAllByRegionId(regionId)
}