package ru.woowy.character.infrastructure.mapper

import ru.woowy.character.domain.model.CharacterGameSettings
import ru.woowy.character.domain.model.CharacterGameSettingsDto
import ru.woowy.character.infrastructure.persistence.entity.CharacterGameSettingsEntity

fun CharacterGameSettingsEntity.asDomain() = CharacterGameSettings(
    characterId = this.characterId,
    speed = this.speed,
)

fun CharacterGameSettings.asDto() = CharacterGameSettingsDto(
    characterId = this.characterId,
    speed = this.speed,
)