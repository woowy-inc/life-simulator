package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId
import ru.woowy.infrastructure.persistence.entity.CharacterEntity

interface CharacterJpaRepository : JpaRepository<CharacterEntity, CharacterId> {
    fun findAllByUserId(userId: UserId): List<CharacterEntity>
}