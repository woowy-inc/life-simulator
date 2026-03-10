package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.Character
import ru.woowy.domain.model.CharacterCreatedEvent
import ru.woowy.domain.model.CharacterDeletedEvent
import ru.woowy.infrastructure.persistence.entity.CharacterEntity
import java.util.UUID
import kotlin.time.Clock

fun CharacterEntity.asDomain() = Character(
    id = this.id,
    userId = this.userId,
    name = this.name,
    gender = this.gender,
    birthday = this.birthday,
    locationId = this.locationId,
    worldId = this.worldId,
    createdAt = this.createdAt,
)

fun Character.asCreatedEvent(): CharacterCreatedEvent = CharacterCreatedEvent(
    eventId = UUID.randomUUID(),
    timestamp = Clock.System.now().toEpochMilliseconds(),
    userId = this.userId,
    characterId = this.id,
    gender = this.gender,
    locationId = this.locationId,
)

fun Character.asDeletedEvent(): CharacterDeletedEvent = CharacterDeletedEvent(
    eventId = UUID.randomUUID(),
    timestamp = Clock.System.now().toEpochMilliseconds(),
    userId = this.userId,
    characterId = this.id,
)

fun Iterable<CharacterEntity>.asDomain() = this.map { it.asDomain() }