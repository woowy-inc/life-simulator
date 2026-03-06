package ru.woowy.domain.repository

import org.springframework.data.domain.Sort
import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.CityPageable
import ru.woowy.infrastructure.model.CitySortColumn

interface CityRepository {
    fun findAll(
        sortColumn: CitySortColumn,
        sortOrder: Sort.Direction,
        search: String?,
        page: Int?,
        count: Int?,
    ): CityPageable

    fun findAll(): CityPageable

    fun findById(cityId: CityId): City?

    fun add(city: City): City

    fun addOrUpdate(cities: List<City>): List<City>

    fun update(city: City): City?

    fun delete(cityId: CityId)

    fun isEmpty(): Boolean
}