package ru.woowy.application.usecase

import ru.woowy.domain.model.Region
import ru.woowy.domain.model.RegionId

internal interface RegionUseCase {
    fun get(regionId: RegionId): Region?

    fun add(region: Region): Region

    fun update(region: Region): Region?

    fun delete(regionId: RegionId)
}