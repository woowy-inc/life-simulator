package ru.woowy.application.usecase.region.namecase

import org.springframework.stereotype.Service
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.repository.RegionNameCaseRepository

@Service
internal class DeleteRegionNameCasesUseCase(
    private val nameCaseRepository: RegionNameCaseRepository,
) {
    operator fun invoke(regionId: RegionId) = nameCaseRepository.delete(regionId)
}