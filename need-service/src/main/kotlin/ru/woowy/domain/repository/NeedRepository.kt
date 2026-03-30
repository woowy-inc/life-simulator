package ru.woowy.domain.repository

import ru.woowy.domain.model.Need
import ru.woowy.id.CharacterId

interface NeedRepository {
    fun findLast(characterId: CharacterId): Need?

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

    fun delete(characterId: CharacterId)
}