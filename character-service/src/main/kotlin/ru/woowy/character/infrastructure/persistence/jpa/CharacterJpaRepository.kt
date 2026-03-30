package ru.woowy.character.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.character.infrastructure.persistence.entity.CharacterEntity
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId

interface CharacterJpaRepository : JpaRepository<CharacterEntity, CharacterId> {
    fun findAllByUserId(userId: UserId): List<CharacterEntity>
}