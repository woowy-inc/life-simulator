package ru.woowy.domain.usecase

import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.CityNameCase

interface CityNameCaseUseCase {
    fun findAll(cityId: CityId): List<CityNameCase>

    fun add(nameCase: CityNameCase): CityNameCase

    fun deleteAll(cityId: CityId)
}