package ru.woowy.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.woowy.id.CharacterId
import ru.woowy.id.EventId
import ru.woowy.id.WorldId
import java.time.LocalDateTime

@Serializable
data class WorldCreatedEvent(
    @Contextual
    override val eventId: EventId,
    override val timestamp: Long,
    @Contextual
    val characterId: CharacterId,
    @Contextual
    val worldId: WorldId,
) : Event

@Serializable
data class WorldTickEvent(
    @Contextual
    override val eventId: EventId,
    override val timestamp: Long,
    @Contextual
    val characterId: CharacterId,
    @Contextual
    val gameTime: LocalDateTime,
    val tickNumber: Long,
) : Event