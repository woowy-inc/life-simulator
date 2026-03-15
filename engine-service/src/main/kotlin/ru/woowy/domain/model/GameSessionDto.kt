package ru.woowy.domain.model

import ru.woowy.id.CharacterId
import java.time.LocalDateTime

data class GameSessionDto(
    val characterId: CharacterId,
    val status: GameStatus,
    val gameTime: LocalDateTime,
    val startedAt: LocalDateTime,
)