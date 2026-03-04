package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.domain.model.RegionId
import ru.woowy.infrastructure.persistence.entity.RegionEntity
import java.util.Optional

interface JpaRegionRepository : JpaRepository<RegionEntity, RegionId> {
    @EntityGraph(attributePaths = ["nameCase"])
    override fun findById(id: RegionId): Optional<RegionEntity>

    @EntityGraph(attributePaths = ["nameCase"])
    override fun findAll(): MutableList<RegionEntity>
}