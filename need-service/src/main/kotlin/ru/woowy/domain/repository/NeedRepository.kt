package ru.woowy.domain.repository

import ru.woowy.domain.model.Need
import ru.woowy.id.CharacterId
import ru.woowy.id.NeedId

interface NeedRepository {
    fun findLast(characterId: CharacterId): Need?

    fun findAllByCharacters(characterIds: Collection<CharacterId>): Map<CharacterId, Need>

    fun add(
        characterId: CharacterId,
        need: Need,
    ): Need

    fun addAll(
        characterId: CharacterId,
        needs: List<Need>,
    )

    fun update(
        characterId: CharacterId,
        need: Need,
    ): Need?

    fun delete(needId: NeedId)

    fun deleteAll(characterId: CharacterId)
}