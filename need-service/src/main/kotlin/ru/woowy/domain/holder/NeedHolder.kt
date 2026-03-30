package ru.woowy.domain.holder

import ru.woowy.domain.model.Need
import ru.woowy.id.CharacterId

interface NeedHolder {
    suspend fun get(characterId: CharacterId): Need?

    suspend fun push(
        characterId: CharacterId,
        need: Need,
    )

    suspend fun popAll(): Map<CharacterId, Need>
}