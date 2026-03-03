package ru.woowy.infrastructure.persistence

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase
import ru.woowy.domain.repository.RegionNameCaseRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.persistence.entity.RegionEntity
import ru.woowy.infrastructure.persistence.entity.RegionNameCaseEntity
import ru.woowy.infrastructure.persistence.jpa.JpaRegionNameCaseRepository
import ru.woowy.infrastructure.persistence.jpa.JpaRegionRepository

@Repository
internal class RegionNameCaseRepositoryImpl(
    private val jpaRegionRepository: JpaRegionRepository,
    private val jpaRegionNameCaseRepository: JpaRegionNameCaseRepository,
) : RegionNameCaseRepository {
    override fun findAllByRegionId(regionId: RegionId): List<RegionNameCase> =
        jpaRegionNameCaseRepository.findAllByRegionId(regionId).map { it.asDomain() }

    override fun add(case: RegionNameCase): RegionNameCase =
        jpaRegionNameCaseRepository.save(case.asEntity(case)).asDomain()

    override fun update(case: RegionNameCase): RegionNameCase? {
        if (!jpaRegionNameCaseRepository.existsById(case.id)) return null

        return jpaRegionNameCaseRepository.save(case.asEntity(case)).asDomain()
    }

    override fun deleteAll(regionId: RegionId) = jpaRegionNameCaseRepository.deleteAllByRegionId(regionId)

    private fun RegionNameCase.asEntity(case: RegionNameCase): RegionNameCaseEntity {
        val region = jpaRegionRepository.getReferenceById(case.regionId)

        return asEntity(region)
    }

    private fun RegionNameCase.asEntity(region: RegionEntity): RegionNameCaseEntity = RegionNameCaseEntity(
        id = this.id,
        region = region,
        nominative = this.nominative,
        genitive = this.genitive,
        dative = this.dative,
        accusative = this.accusative,
        ablative = this.ablative,
        prepositional = this.prepositional,
        locative = this.locative,
    )
}