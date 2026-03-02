package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.repository.CrudRepository
import ru.woowy.domain.model.RegionId
import ru.woowy.infrastructure.persistence.entity.RegionEntity

internal interface CrudRegionRepository : CrudRepository<RegionEntity, RegionId>