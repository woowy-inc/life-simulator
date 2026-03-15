package ru.woowy.domain.model

import ru.woowy.game.GameSpeed
import ru.woowy.id.CharacterId

data class GameSettings(
    val characterId: CharacterId,
    val speed: GameSpeed,
)