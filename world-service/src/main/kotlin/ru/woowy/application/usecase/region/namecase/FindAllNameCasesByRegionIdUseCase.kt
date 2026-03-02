package ru.woowy.application.usecase.region.namecase

import org.springframework.stereotype.Service
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase
import ru.woowy.domain.repository.RegionNameCaseRepository

@Service
internal class FindAllNameCasesByRegionIdUseCase(
    private val nameCaseRepository: RegionNameCaseRepository,
) {
    operator fun invoke(regionId: RegionId): List<RegionNameCase> = nameCaseRepository.findAllByRegionId(regionId)
}