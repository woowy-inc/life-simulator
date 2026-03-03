package ru.woowy.application.usecase

import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase

internal interface RegionNameCaseUseCase {
    fun getAll(regionId: RegionId): List<RegionNameCase>

    fun add(nameCase: RegionNameCase): RegionNameCase

    fun update(nameCase: RegionNameCase): RegionNameCase?

    fun delete(regionId: RegionId)
}