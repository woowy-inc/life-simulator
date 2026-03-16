package ru.woowy.character.domain.repository

import ru.woowy.character.domain.model.CharacterGameSettings
import ru.woowy.id.CharacterId

interface CharacterGameSettingsRepository {
    fun findById(characterId: CharacterId): CharacterGameSettings?

    fun addOrUpdate(settings: CharacterGameSettings): CharacterGameSettings

    fun delete(characterId: CharacterId)
}