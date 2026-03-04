package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase
import ru.woowy.domain.repository.RegionNameCaseRepository
import ru.woowy.domain.usecase.RegionNameCaseUseCase

@Service
class RegionNameCaseUseCaseImpl(
    private val caseRepository: RegionNameCaseRepository,
) : RegionNameCaseUseCase {
    override fun getAll(regionId: RegionId): List<RegionNameCase> = caseRepository.findAllByRegionId(regionId)

    override fun add(nameCase: RegionNameCase): RegionNameCase = caseRepository.add(nameCase)

    override fun update(nameCase: RegionNameCase): RegionNameCase? = caseRepository.update(nameCase)

    override fun delete(regionId: RegionId) = caseRepository.deleteAll(regionId)
}