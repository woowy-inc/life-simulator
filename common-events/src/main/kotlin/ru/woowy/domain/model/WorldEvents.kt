package ru.woowy.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.woowy.id.CharacterId
import ru.woowy.id.EventId
import ru.woowy.id.WorldId

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