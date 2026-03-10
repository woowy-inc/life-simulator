package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.id.CharacterId
import ru.woowy.id.WorldId
import ru.woowy.infrastructure.persistence.entity.WorldEntity

interface WorldJpaRepository : JpaRepository<WorldEntity, WorldId> {
    fun findFirstByCharacterId(characterId: CharacterId): WorldEntity?

    fun deleteAllByCharacterId(characterId: CharacterId)
}