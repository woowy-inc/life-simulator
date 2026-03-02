package ru.woowy.application.usecase.region.namecase

import org.springframework.stereotype.Service
import ru.woowy.domain.model.RegionNameCase
import ru.woowy.domain.repository.RegionNameCaseRepository

@Service
internal class AddRegionNameCaseUseCase(
    private val nameCaseRepository: RegionNameCaseRepository,
) {
    operator fun invoke(nameCase: RegionNameCase): RegionNameCase = nameCaseRepository.add(nameCase)
}