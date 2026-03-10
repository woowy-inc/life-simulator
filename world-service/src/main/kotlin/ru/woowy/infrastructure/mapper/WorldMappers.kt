package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.World
import ru.woowy.domain.model.WorldCreatedEvent
import ru.woowy.infrastructure.persistence.entity.WorldEntity
import java.util.UUID
import kotlin.time.Clock

fun WorldEntity.asDomain() = World(
    id = this.id,
    characterId = this.characterId,
    createdAt = this.createdAt,
)

fun World.asCreatedEvent(): WorldCreatedEvent = WorldCreatedEvent(
    eventId = UUID.randomUUID(),
    timestamp = Clock.System.now().toEpochMilliseconds(),
    characterId = this.characterId,
    worldId = this.id,
)