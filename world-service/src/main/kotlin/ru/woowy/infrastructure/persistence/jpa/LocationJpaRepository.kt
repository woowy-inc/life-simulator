package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import ru.woowy.domain.model.LocationId
import ru.woowy.infrastructure.persistence.entity.LocationEntity
import ru.woowy.infrastructure.persistence.spec.RegionSpecs
import ru.woowy.infrastructure.persistence.spec.TimezoneSpecs
import java.util.Optional

interface LocationJpaRepository :
    JpaRepository<LocationEntity, LocationId>,
    JpaSpecificationExecutor<LocationEntity> {
    @EntityGraph(attributePaths = [RegionSpecs.REGION, TimezoneSpecs.TIMEZONE])
    override fun findAll(
        spec: Specification<LocationEntity>,
        pageable: Pageable,
    ): Page<LocationEntity>

    @EntityGraph(attributePaths = [RegionSpecs.REGION, TimezoneSpecs.TIMEZONE])
    override fun findById(id: LocationId): Optional<LocationEntity>

    fun existsBy(): Boolean
}