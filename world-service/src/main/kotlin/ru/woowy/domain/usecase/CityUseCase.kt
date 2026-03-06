package ru.woowy.domain.usecase

import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.Importable

interface CityUseCase : Importable<City> {
    fun get(cityId: CityId): City?

    fun add(city: City): City

    fun update(city: City): City?

    fun delete(cityId: CityId)

    fun isEmpty(): Boolean
}