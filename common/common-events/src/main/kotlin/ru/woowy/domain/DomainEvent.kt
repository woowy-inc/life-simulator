package ru.woowy.domain

import ru.woowy.game.GameConfig
import java.time.Instant
import java.util.UUID

sealed interface DomainEvent {
    val id: UUID
    val worldId: UUID

    data class WorldTickEvent(
        override val id: UUID = UUID.randomUUID(),
        override val worldId: UUID,
        val config: GameConfig,
        val startedAt: Instant,
        val currentTime: Instant,
    ) : DomainEvent
}