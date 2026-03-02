package ru.woowy.application.usecase.region

import org.springframework.stereotype.Service
import ru.woowy.domain.model.Region
import ru.woowy.domain.repository.RegionRepository

@Service
internal class UpdateRegionUseCase(
    private val regionRepository: RegionRepository,
) {
    operator fun invoke(region: Region): Region? = regionRepository.update(region)
}