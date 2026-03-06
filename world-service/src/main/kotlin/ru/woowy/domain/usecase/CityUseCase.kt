package ru.woowy.domain.usecase

import org.springframework.data.domain.Sort
import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.CityPageable
import ru.woowy.domain.model.Importable
import ru.woowy.infrastructure.model.CitySortColumn

interface CityUseCase : Importable<City> {
    fun getAll(
        sortColumn: CitySortColumn,
        sortOrder: Sort.Direction,
        search: String?,
        page: Int?,
        count: Int?,
    ): CityPageable

    fun get(cityId: CityId): City?

    fun add(city: City): City

    fun update(city: City): City?

    fun delete(cityId: CityId)

    fun isEmpty(): Boolean
}