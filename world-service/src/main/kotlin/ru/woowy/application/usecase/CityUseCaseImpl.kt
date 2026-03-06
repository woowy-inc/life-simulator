package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId
import ru.woowy.domain.repository.CityRepository
import ru.woowy.domain.usecase.CityUseCase

@Service
class CityUseCaseImpl(
    private val cityRepository: CityRepository,
) : CityUseCase {
    override fun get(cityId: CityId): City? = cityRepository.findById(cityId)

    override fun add(city: City): City = cityRepository.add(city)

    override fun update(city: City): City? = cityRepository.update(city)

    override fun delete(cityId: CityId) = cityRepository.delete(cityId)

    override fun isEmpty(): Boolean = cityRepository.isEmpty()

    override fun import(data: List<City>): List<City> = cityRepository.addOrUpdate(data)
}