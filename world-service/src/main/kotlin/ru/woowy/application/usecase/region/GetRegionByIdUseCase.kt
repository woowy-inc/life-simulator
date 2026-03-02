package ru.woowy.application.usecase.region

import org.springframework.stereotype.Service
import ru.woowy.domain.model.Region
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.repository.RegionRepository

@Service
internal class GetRegionByIdUseCase(
    private val regionRepository: RegionRepository,
) {
    operator fun invoke(regionId: RegionId): Region? = regionRepository.findById(regionId)
}