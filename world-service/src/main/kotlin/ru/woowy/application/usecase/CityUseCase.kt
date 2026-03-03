package ru.woowy.application.usecase

import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId

internal interface CityUseCase {
    fun get(cityId: CityId): City?

    fun add(city: City): City

    fun update(city: City): City?

    fun delete(cityId: CityId)
}