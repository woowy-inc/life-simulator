package ru.woowy.helper

import ru.woowy.character.Gender
import ru.woowy.domain.model.Character
import ru.woowy.id.CharacterId
import ru.woowy.id.LocationId
import ru.woowy.id.UserId
import ru.woowy.id.WorldId
import ru.woowy.util.randomGender
import ru.woowy.util.randomLocalDateTime
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID
import java.time.LocalDateTime

fun randomCharacter(
    id: CharacterId = randomUUID(),
    userId: UserId = randomUUID(),
    name: String = randomString(),
    gender: Gender = randomGender(),
    birthday: LocalDateTime = randomLocalDateTime(),
    locationId: LocationId = randomUUID(),
    worldId: WorldId? = null,
    createdAt: LocalDateTime = randomLocalDateTime(),
): Character = Character(
    id = id,
    userId = userId,
    name = name,
    gender = gender,
    birthday = birthday,
    locationId = locationId,
    worldId = worldId,
    createdAt = createdAt,
)