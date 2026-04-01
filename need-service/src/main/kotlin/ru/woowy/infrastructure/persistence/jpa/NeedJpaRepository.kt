package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.persistence.entity.NeedEntity
import java.util.UUID

interface NeedJpaRepository : JpaRepository<NeedEntity, CharacterId> {
    @Query("select n from needs n where n.characterId = :characterId order by n.createdAt desc limit 1")
    fun findLast(characterId: CharacterId): NeedEntity?

    fun deleteAllByCharacterId(characterId: UUID)
}