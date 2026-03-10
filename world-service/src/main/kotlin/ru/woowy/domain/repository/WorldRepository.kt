package ru.woowy.domain.repository

import ru.woowy.domain.model.World
import ru.woowy.id.CharacterId
import ru.woowy.id.WorldId

interface WorldRepository {
    fun findById(worldId: WorldId): World?

    fun findByCharacter(characterId: CharacterId): World?

    fun add(world: World): World

    fun delete(worldId: WorldId)

    fun deleteByCharacter(characterId: CharacterId)
}