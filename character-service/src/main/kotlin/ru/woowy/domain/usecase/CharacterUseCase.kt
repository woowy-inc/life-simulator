package ru.woowy.domain.usecase

import ru.woowy.domain.model.Character
import ru.woowy.domain.model.CharacterRequest
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId

interface CharacterUseCase {
    fun create(
        request: CharacterRequest,
        userId: UserId,
    ): Character

    fun get(characterId: CharacterId): Character?

    fun getAll(userId: UserId): List<Character>

    fun update(character: Character): Character?

    fun delete(characterId: CharacterId)
}