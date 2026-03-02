package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.repository.CrudRepository
import ru.woowy.domain.model.RegionId
import ru.woowy.infrastructure.persistence.entity.RegionNameCaseEntity
import java.util.UUID

internal interface CrudRegionNameCaseRepository : CrudRepository<RegionNameCaseEntity, RegionId> {
    fun findAllByRegionId(regionId: RegionId): List<RegionNameCaseEntity>

    fun deleteAllByRegionId(regionId: UUID)
}