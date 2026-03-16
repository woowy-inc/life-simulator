package ru.woowy.character.domain.usecase

import ru.woowy.character.domain.model.Character
import ru.woowy.character.domain.model.CharacterRequest
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId
import ru.woowy.id.WorldId

interface CharacterUseCase {
    fun create(
        request: CharacterRequest,
        owner: UserId,
    ): Character

    fun get(characterId: CharacterId): Character?

    fun getAll(owner: UserId): List<Character>

    fun update(
        characterId: CharacterId,
        request: CharacterRequest,
        owner: UserId,
    ): Character?

    fun update(
        characterId: CharacterId,
        worldId: WorldId,
    ): Character?

    fun delete(
        characterId: CharacterId,
        owner: UserId,
    )
}