package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import ru.woowy.domain.model.CityId
import ru.woowy.infrastructure.persistence.entity.CityEntity
import ru.woowy.infrastructure.persistence.spec.RegionSpecs
import ru.woowy.infrastructure.persistence.spec.TimezoneSpecs
import java.util.Optional

interface JpaCityRepository :
    JpaRepository<CityEntity, CityId>,
    JpaSpecificationExecutor<CityEntity> {
    @EntityGraph(attributePaths = [RegionSpecs.REGION, TimezoneSpecs.TIMEZONE])
    override fun findAll(
        spec: Specification<CityEntity>,
        pageable: Pageable,
    ): Page<CityEntity>

    @EntityGraph(attributePaths = [RegionSpecs.REGION, TimezoneSpecs.TIMEZONE])
    override fun findById(id: CityId): Optional<CityEntity>

    fun existsBy(): Boolean
}