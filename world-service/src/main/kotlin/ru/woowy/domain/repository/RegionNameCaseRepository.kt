package ru.woowy.domain.repository

import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase

internal interface RegionNameCaseRepository {
    fun findAllByRegionId(regionId: RegionId): List<RegionNameCase>

    fun add(nameCase: RegionNameCase): RegionNameCase

    fun add(nameCases: List<RegionNameCase>): List<RegionNameCase>

    fun deleteAll(regionId: RegionId)
}