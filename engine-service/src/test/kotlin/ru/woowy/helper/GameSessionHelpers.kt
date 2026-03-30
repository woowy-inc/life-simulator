package ru.woowy.helper

import ru.woowy.domain.model.GameSession
import ru.woowy.domain.model.GameSessionRequest
import ru.woowy.domain.model.GameStatus
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId
import ru.woowy.util.randomLocalDateTime
import ru.woowy.util.randomLong
import ru.woowy.util.randomUUID
import java.time.LocalDateTime

fun randomGameSession(
    characterId: CharacterId = randomUUID(),
    status: GameStatus = GameStatus.entries.random(),
    gameTime: LocalDateTime = randomLocalDateTime(),
    startedAt: LocalDateTime = randomLocalDateTime(),
    startedBy: UserId = randomUUID(),
    tickNumber: Long = randomLong(),
    pausedAt: LocalDateTime? = randomLocalDateTime(),
): GameSession = GameSession(
    characterId = characterId,
    status = status,
    gameTime = gameTime,
    startedAt = startedAt,
    startedBy = startedBy,
    tickNumber = tickNumber,
    pausedAt = pausedAt,
)

fun randomGameSessionRequest(characterId: CharacterId = randomUUID()): GameSessionRequest = GameSessionRequest(
    characterId = characterId,
)