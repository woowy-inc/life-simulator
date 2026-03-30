package ru.woowy.domain.model

import ru.woowy.id.CharacterId
import ru.woowy.id.UserId
import java.time.LocalDateTime

data class GameSession(
    val characterId: CharacterId,
    val status: GameStatus,
    val gameTime: LocalDateTime,
    val startedAt: LocalDateTime,
    val startedBy: UserId,
    val tickNumber: Long,
    val pausedAt: LocalDateTime?,
)