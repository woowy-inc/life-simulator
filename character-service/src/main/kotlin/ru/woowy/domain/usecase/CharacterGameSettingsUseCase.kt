package ru.woowy.domain.usecase

import ru.woowy.domain.model.CharacterGameSettings
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId

interface CharacterGameSettingsUseCase {
    fun get(
        characterId: CharacterId,
        owner: UserId,
    ): CharacterGameSettings?

    fun addOrUpdate(
        settings: CharacterGameSettings,
        owner: UserId,
    ): CharacterGameSettings

    fun delete(
        characterId: CharacterId,
        owner: UserId,
    )
}