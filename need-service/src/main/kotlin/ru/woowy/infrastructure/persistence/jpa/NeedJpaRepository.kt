package ru.woowy.infrastructure.persistence.jpa

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.persistence.entity.NeedEntity

interface NeedJpaRepository : JpaRepository<NeedEntity, CharacterId> {
    @Query("select n from NeedEntity n where n.characterId = :characterId order by n.createdAt desc limit 1")
    fun findLast(characterId: CharacterId): NeedEntity?

    @Query("select n from NeedEntity n where n.characterId in :characterIds")
    fun findAllByCharacterId(characterIds: Collection<CharacterId>): Collection<NeedEntity>

    fun deleteAllByCharacterId(characterId: UUID)
}