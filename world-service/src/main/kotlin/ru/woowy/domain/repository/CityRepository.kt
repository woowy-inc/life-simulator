package ru.woowy.domain.repository

import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId

interface CityRepository {
    fun findAll(): List<City>

    fun findById(cityId: CityId): City?

    fun add(city: City): City

    fun addOrUpdate(cities: List<City>): List<City>

    fun update(city: City): City?

    fun delete(cityId: CityId)

    fun isEmpty(): Boolean
}