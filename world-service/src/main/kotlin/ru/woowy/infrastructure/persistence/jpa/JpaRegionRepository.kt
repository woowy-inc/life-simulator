package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.domain.model.RegionId
import ru.woowy.infrastructure.persistence.entity.RegionEntity

interface JpaRegionRepository : JpaRepository<RegionEntity, RegionId>