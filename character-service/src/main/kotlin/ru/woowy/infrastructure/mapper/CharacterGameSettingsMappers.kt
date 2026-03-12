package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.CharacterGameSettings
import ru.woowy.domain.model.CharacterGameSettingsDto
import ru.woowy.infrastructure.persistence.entity.CharacterGameSettingsEntity

fun CharacterGameSettingsEntity.asDomain() = CharacterGameSettings(
    characterId = this.characterId,
    speed = this.speed,
)

fun CharacterGameSettings.asDto() = CharacterGameSettingsDto(
    characterId = this.characterId,
    speed = this.speed,
)