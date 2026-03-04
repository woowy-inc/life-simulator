package ru.woowy.domain.repository

import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase

interface RegionNameCaseRepository {
    fun findAllByRegionId(regionId: RegionId): List<RegionNameCase>

    fun add(case: RegionNameCase): RegionNameCase

    fun update(case: RegionNameCase): RegionNameCase?

    fun deleteAll(regionId: RegionId)
}