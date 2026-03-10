package ru.woowy.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.woowy.character.Gender
import ru.woowy.id.CharacterId
import ru.woowy.id.EventId
import ru.woowy.id.LocationId
import ru.woowy.id.UserId

@Serializable
data class CharacterCreatedEvent(
    @Contextual
    override val eventId: EventId,
    override val timestamp: Long,
    @Contextual
    val userId: UserId,
    @Contextual
    val characterId: CharacterId,
    val gender: Gender,
    @Contextual
    val locationId: LocationId,
) : Event

@Serializable
data class CharacterDeletedEvent(
    @Contextual
    override val eventId: EventId,
    override val timestamp: Long,
    @Contextual
    val userId: UserId,
    @Contextual
    val characterId: CharacterId,
) : Event