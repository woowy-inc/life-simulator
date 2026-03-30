package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import ru.woowy.domain.model.GameStatus
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId
import java.time.LocalDateTime

@Entity(name = "game_sessions")
class GameSessionEntity(
    @Id
    @Column(name = "character_id", nullable = false)
    var characterId: CharacterId,
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var status: GameStatus,
    @Column(name = "game_time", nullable = false)
    var gameTime: LocalDateTime,
    @Column(name = "started_at", nullable = false)
    var startedAt: LocalDateTime,
    @Column(name = "started_by", nullable = false)
    var startedBy: UserId,
    @Column(name = "tick_number", nullable = false)
    var tickNumber: Long,
    @Column(name = "paused_at", nullable = true)
    var pausedAt: LocalDateTime? = null,
)