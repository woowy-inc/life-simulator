package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.repository.CrudRepository
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase
import ru.woowy.infrastructure.persistence.entity.RegionNameCaseEntity
import java.util.UUID

internal interface CrudRegionNameCaseRepository : CrudRepository<RegionNameCaseEntity, RegionId> {
    fun findAllByRegionId(regionId: RegionId): List<RegionNameCaseEntity>

    fun insertAllByRegionId(
        regionId: RegionId,
        nameCases: List<RegionNameCase>,
    ): List<RegionNameCase>

    fun deleteAllByRegionId(regionId: UUID)
}