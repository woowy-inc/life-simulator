package ru.woowy.domain.repository

import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.CityNameCase

interface CityNameCaseRepository {
    fun findAllByCityId(cityId: CityId): List<CityNameCase>

    fun add(case: CityNameCase): CityNameCase

    fun deleteAll(cityId: CityId)
}