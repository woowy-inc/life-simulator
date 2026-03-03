package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.domain.model.CityId
import ru.woowy.infrastructure.persistence.entity.CityEntity
import java.util.Optional

internal interface JpaCityRepository : JpaRepository<CityEntity, CityId> {
    @EntityGraph(attributePaths = ["region", "region.nameCase", "timezone", "nameCase"])
    override fun findAll(): List<CityEntity>

    @EntityGraph(attributePaths = ["region", "region.nameCase", "timezone", "nameCase"])
    override fun findById(id: CityId): Optional<CityEntity>
}