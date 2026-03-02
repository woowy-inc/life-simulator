package ru.woowy.application.usecase.region

import org.springframework.stereotype.Service
import ru.woowy.domain.model.Region
import ru.woowy.domain.repository.RegionRepository

@Service
internal class AddRegionUseCase(
    private val regionRepository: RegionRepository,
) {
    operator fun invoke(region: Region): Region = regionRepository.add(region)
}