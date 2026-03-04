package ru.woowy.domain.usecase

import ru.woowy.domain.model.Region
import ru.woowy.domain.model.RegionId

interface RegionUseCase {
    fun get(regionId: RegionId): Region?

    fun add(region: Region): Region

    fun update(region: Region): Region?

    fun delete(regionId: RegionId)
}