package ru.woowy.application.usecase.impl

import org.springframework.stereotype.Service
import ru.woowy.application.usecase.RegionUseCase
import ru.woowy.domain.model.Region
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.repository.RegionRepository

@Service
internal class RegionUseCaseImpl(
    private val regionRepository: RegionRepository,
) : RegionUseCase {
    override fun get(regionId: RegionId): Region? = regionRepository.findById(regionId)

    override fun add(region: Region): Region = regionRepository.add(region)

    override fun update(region: Region): Region? = regionRepository.update(region)

    override fun delete(regionId: RegionId) = regionRepository.delete(regionId)
}