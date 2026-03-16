package ru.woowy.helper

import ru.woowy.character.domain.model.CharacterGameSettings
import ru.woowy.game.GameSpeed
import ru.woowy.id.CharacterId
import ru.woowy.util.randomUUID

fun randomCharacterGameSettings(
    characterId: CharacterId = randomUUID(),
    speed: GameSpeed = GameSpeed.entries.random(),
): CharacterGameSettings = CharacterGameSettings(
    characterId = characterId,
    speed = speed,
)