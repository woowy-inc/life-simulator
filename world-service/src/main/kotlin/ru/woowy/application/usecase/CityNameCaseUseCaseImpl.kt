package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.CityNameCase
import ru.woowy.domain.repository.CityNameCaseRepository
import ru.woowy.domain.usecase.CityNameCaseUseCase

@Service
class CityNameCaseUseCaseImpl(
    private val nameCaseRepository: CityNameCaseRepository,
) : CityNameCaseUseCase {
    override fun findAll(cityId: CityId): List<CityNameCase> = nameCaseRepository.findAllByCityId(cityId)

    override fun add(nameCase: CityNameCase): CityNameCase = nameCaseRepository.add(nameCase)

    override fun deleteAll(cityId: CityId) = nameCaseRepository.deleteAll(cityId)
}