package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.Character
import ru.woowy.infrastructure.persistence.entity.CharacterEntity

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

fun Iterable<CharacterEntity>.asDomain() = this.map { it.asDomain() }