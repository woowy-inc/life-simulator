package ru.woowy.application.usecase.impl

import org.springframework.stereotype.Service
import ru.woowy.application.usecase.CityUseCase
import ru.woowy.domain.model.City
import ru.woowy.domain.model.CityId
import ru.woowy.domain.repository.CityRepository

@Service
internal class CityUseCaseImpl(
    private val cityRepository: CityRepository,
) : CityUseCase {
    override fun get(cityId: CityId): City? = cityRepository.findById(cityId)

    override fun add(city: City): City = cityRepository.add(city)

    override fun update(city: City): City? = cityRepository.update(city)

    override fun delete(cityId: CityId) = cityRepository.delete(cityId)
}