package ru.woowy.application.usecase.region.namecase

import org.springframework.stereotype.Service
import ru.woowy.domain.model.RegionNameCase
import ru.woowy.domain.repository.RegionNameCaseRepository

@Service
internal class UpdateRegionNameCasesUseCase(
    private val nameCaseRepository: RegionNameCaseRepository,
) {
    operator fun invoke(nameCases: List<RegionNameCase>): List<RegionNameCase> {
        nameCases
            .groupBy { case -> case.regionId }
            .forEach { (regionId, _) -> nameCaseRepository.deleteAll(regionId) }

        return nameCaseRepository.add(nameCases)
    }
}