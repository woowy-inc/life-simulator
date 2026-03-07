package ru.woowy.application.usecase

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.CityPageable
import ru.woowy.domain.repository.CityRepository
import ru.woowy.domain.usecase.CityUseCase
import ru.woowy.infrastructure.model.CitySortColumn

@Service
class CityUseCaseImpl(
    private val cityRepository: CityRepository,
) : CityUseCase {
    override fun getAll(
        sortColumn: CitySortColumn,
        sortOrder: Sort.Direction,
        search: String?,
        page: Int?,
        count: Int?,
    ): CityPageable = cityRepository.findAll(
        sortColumn = sortColumn,
        sortOrder = sortOrder,
        search = search,
        page = page,
        count = count,
    )

    override fun get(cityId: CityId): City? = cityRepository.findById(cityId)

    override fun add(city: City): City = cityRepository.add(city)

    override fun update(city: City): City? = cityRepository.update(city)

    override fun delete(cityId: CityId) = cityRepository.delete(cityId)

    override fun isEmpty(): Boolean = cityRepository.isEmpty()

    override fun import(data: List<City>): List<City> = cityRepository.addOrUpdate(data)
}