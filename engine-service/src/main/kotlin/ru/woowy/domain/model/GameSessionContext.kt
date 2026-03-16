package ru.woowy.domain.model

import ru.woowy.id.CharacterId

data class GameSessionContext(
    val characterId: CharacterId,
    val session: GameSession,
    val settings: GameSettings,
)