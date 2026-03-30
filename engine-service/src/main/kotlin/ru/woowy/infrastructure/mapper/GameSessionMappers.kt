package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.GameSession
import ru.woowy.domain.model.GameSessionDto
import ru.woowy.domain.model.WorldTickEvent
import ru.woowy.game.GameSpeed
import ru.woowy.infrastructure.persistence.entity.GameSessionEntity
import java.util.UUID
import kotlin.time.Clock

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

fun GameSession.asWorldTickEvent(gameSpeed: GameSpeed): WorldTickEvent = WorldTickEvent(
    eventId = UUID.randomUUID(),
    timestamp = Clock.System.now().toEpochMilliseconds(),
    characterId = this.characterId,
    gameTime = this.gameTime,
    tickNumber = this.tickNumber,
    gameSpeed = gameSpeed,
)