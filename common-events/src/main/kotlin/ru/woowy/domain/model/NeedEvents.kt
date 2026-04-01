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
    override val characterId: CharacterId,
    val tickNumber: Long,
    val hunger: Double,
    val sleep: Double,
    val body: Double,
    val mental: Double,
    val social: Double,
    val health: Double,
    val happiness: Double,
) : CharacterStateEvent