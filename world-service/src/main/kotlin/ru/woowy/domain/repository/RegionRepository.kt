package ru.woowy.domain.repository

import ru.woowy.domain.model.Region
import ru.woowy.domain.model.RegionId

interface RegionRepository {
    fun findById(regionId: RegionId): Region?

    fun add(region: Region): Region

    fun addOrUpdate(regions: List<Region>): List<Region>

    fun update(region: Region): Region?

    fun delete(regionId: RegionId)
}