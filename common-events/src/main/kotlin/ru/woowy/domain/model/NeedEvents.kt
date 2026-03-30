package ru.woowy.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.woowy.id.CharacterId
import ru.woowy.id.EventId

@Serializable
data class NeedUpdatedEvent(
    @Contextual
    override val eventId: EventId,
    override val timestamp: Long,
    @Contextual
    val characterId: CharacterId,
    val tickNumber: Long,
    @Contextual
    val hunger: Double,
    @Contextual
    val sleep: Double,
    @Contextual
    val body: Double,
    @Contextual
    val mental: Double,
    @Contextual
    val social: Double,
    @Contextual
    val health: Double,
    @Contextual
    val happiness: Double,
) : Event