package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.domain.model.CityNameCaseId
import ru.woowy.infrastructure.persistence.entity.CityNameCaseEntity
import java.util.UUID

internal interface JpaCityNameCaseRepository : JpaRepository<CityNameCaseEntity, CityNameCaseId> {
    fun findAllByCityId(cityId: UUID): List<CityNameCaseEntity>

    fun deleteAllByCityId(cityId: UUID)
}