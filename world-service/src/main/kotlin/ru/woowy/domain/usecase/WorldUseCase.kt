package ru.woowy.domain.usecase

import ru.woowy.domain.model.World
import ru.woowy.domain.model.WorldRequest
import ru.woowy.id.CharacterId
import ru.woowy.id.WorldId

interface WorldUseCase {
    fun get(worldId: WorldId): World?

    fun getByCharacter(characterId: CharacterId): World?

    fun add(request: WorldRequest): World

    fun delete(worldId: WorldId)

    fun deleteByCharacter(characterId: CharacterId)
}