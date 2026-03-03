package ru.woowy.application.usecase

import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.CityNameCase

internal interface CityNameCaseUseCase {
    fun findAll(cityId: CityId): List<CityNameCase>

    fun add(nameCase: CityNameCase): CityNameCase

    fun deleteAll(cityId: CityId)
}