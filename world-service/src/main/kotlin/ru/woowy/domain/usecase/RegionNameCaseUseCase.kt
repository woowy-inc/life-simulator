package ru.woowy.domain.usecase

import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase

interface RegionNameCaseUseCase {
    fun getAll(regionId: RegionId): List<RegionNameCase>

    fun add(nameCase: RegionNameCase): RegionNameCase

    fun update(nameCase: RegionNameCase): RegionNameCase?

    fun delete(regionId: RegionId)
}