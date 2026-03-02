package ru.woowy.application.usecase.region.namecase

import org.springframework.stereotype.Service
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase
import ru.woowy.domain.repository.RegionNameCaseRepository

@Service
internal class UpdateRegionNameCasesUseCase(
    private val nameCaseRepository: RegionNameCaseRepository,
) {
    operator fun invoke(
        regionId: RegionId,
        nameCases: List<RegionNameCase>,
    ): List<RegionNameCase> {
        nameCaseRepository.delete(regionId)
        return nameCaseRepository.add(regionId, nameCases)
    }
}