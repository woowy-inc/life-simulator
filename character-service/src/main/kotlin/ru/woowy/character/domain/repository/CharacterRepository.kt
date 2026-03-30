package ru.woowy.character.domain.repository

import ru.woowy.character.domain.model.Character
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId

interface CharacterRepository {
    fun add(character: Character): Character

    fun findById(characterId: CharacterId): Character?

    fun findAllByUser(userId: UserId): List<Character>

    fun update(character: Character): Character

    fun delete(characterId: CharacterId)
}