package ru.woowy.infrastructure.persistence

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.CityId
import ru.woowy.domain.model.CityNameCase
import ru.woowy.domain.repository.CityNameCaseRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.persistence.entity.CityEntity
import ru.woowy.infrastructure.persistence.entity.CityNameCaseEntity
import ru.woowy.infrastructure.persistence.jpa.JpaCityNameCaseRepository
import ru.woowy.infrastructure.persistence.jpa.JpaCityRepository

@Repository
internal class CityNameCaseRepositoryImpl(
    private val jpaCityNameCaseRepository: JpaCityNameCaseRepository,
    private val jpaCityRepository: JpaCityRepository,
) : CityNameCaseRepository {
    override fun findAllByCityId(cityId: CityId): List<CityNameCase> =
        jpaCityNameCaseRepository.findAllByCityId(cityId).map { it.asDomain() }

    override fun add(case: CityNameCase): CityNameCase = jpaCityNameCaseRepository.save(case.asEntity(case)).asDomain()

    override fun deleteAll(cityId: CityId) = jpaCityNameCaseRepository.deleteAllByCityId(cityId)

    private fun CityNameCase.asEntity(case: CityNameCase): CityNameCaseEntity {
        val city = jpaCityRepository.getReferenceById(case.cityId)

        return asEntity(city)
    }

    private fun CityNameCase.asEntity(city: CityEntity): CityNameCaseEntity = CityNameCaseEntity(
        id = this.id,
        city = city,
        nominative = this.nominative,
        genitive = this.genitive,
        dative = this.dative,
        accusative = this.accusative,
        ablative = this.ablative,
        prepositional = this.prepositional,
        locative = this.locative,
    )
}