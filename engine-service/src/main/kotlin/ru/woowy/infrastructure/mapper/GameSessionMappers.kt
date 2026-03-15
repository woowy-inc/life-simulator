package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.GameSession
import ru.woowy.domain.model.GameSessionDto
import ru.woowy.infrastructure.persistence.entity.GameSessionEntity

fun GameSessionEntity.asDomain(): GameSession = GameSession(
    characterId = this.characterId,
    status = this.status,
    gameTime = this.gameTime,
    startedAt = this.startedAt,
    startedBy = this.startedBy,
    tickNumber = this.tickNumber,
    pausedAt = this.pausedAt,
)

fun GameSession.asDto(): GameSessionDto = GameSessionDto(
    characterId = this.characterId,
    status = this.status,
    gameTime = this.gameTime,
    startedAt = this.startedAt,
)