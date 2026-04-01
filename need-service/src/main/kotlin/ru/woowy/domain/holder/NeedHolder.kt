package ru.woowy.domain.holder

import ru.woowy.domain.model.Need
import ru.woowy.id.CharacterId

interface NeedHolder {
    fun get(characterId: CharacterId): Need?

    fun push(
        characterId: CharacterId,
        need: Need,
    )

    fun popAll(): Map<CharacterId, Need>

    fun delete(characterId: CharacterId)

    fun deleteAll()
}