package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.domain.model.RegionId
import ru.woowy.infrastructure.persistence.entity.RegionNameCaseEntity
import java.util.UUID

internal interface JpaRegionNameCaseRepository : JpaRepository<RegionNameCaseEntity, RegionId> {
    fun findAllByRegionId(regionId: RegionId): List<RegionNameCaseEntity>

    fun deleteAllByRegionId(regionId: UUID)
}