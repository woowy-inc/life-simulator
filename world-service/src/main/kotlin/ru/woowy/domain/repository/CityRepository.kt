package ru.woowy.domain.repository

import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId

internal interface CityRepository {
    fun findAll(): List<City>

    fun findById(cityId: CityId): City?

    fun add(city: City): City

    fun update(city: City): City?

    fun delete(cityId: CityId)
}