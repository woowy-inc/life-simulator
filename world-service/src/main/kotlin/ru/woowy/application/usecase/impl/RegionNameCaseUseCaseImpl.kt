package ru.woowy.application.usecase.impl

import org.springframework.stereotype.Service
import ru.woowy.application.usecase.RegionNameCaseUseCase
import ru.woowy.domain.model.RegionId
import ru.woowy.domain.model.RegionNameCase
import ru.woowy.domain.repository.RegionNameCaseRepository

@Service
internal class RegionNameCaseUseCaseImpl(
    private val caseRepository: RegionNameCaseRepository,
) : RegionNameCaseUseCase {
    override fun getAll(regionId: RegionId): List<RegionNameCase> = caseRepository.findAllByRegionId(regionId)

    override fun add(nameCase: RegionNameCase): RegionNameCase = caseRepository.add(nameCase)

    override fun add(nameCases: List<RegionNameCase>): List<RegionNameCase> = caseRepository.add(nameCases)

    override fun update(nameCases: List<RegionNameCase>): List<RegionNameCase> {
        nameCases
            .groupBy { case -> case.regionId }
            .forEach { (regionId, _) -> caseRepository.deleteAll(regionId) }

        return caseRepository.add(nameCases)
    }

    override fun delete(regionId: RegionId) = caseRepository.deleteAll(regionId)
}