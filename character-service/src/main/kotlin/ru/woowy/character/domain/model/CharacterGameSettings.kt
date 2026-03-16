package ru.woowy.character.domain.model

import ru.woowy.game.GameSpeed
import ru.woowy.id.CharacterId

data class CharacterGameSettings(
    val characterId: CharacterId,
    val speed: GameSpeed,
)