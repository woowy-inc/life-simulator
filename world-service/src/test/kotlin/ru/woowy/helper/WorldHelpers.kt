package ru.woowy.helper

import ru.woowy.domain.model.World
import ru.woowy.domain.model.WorldRequest
import ru.woowy.id.CharacterId
import ru.woowy.id.WorldId
import ru.woowy.util.randomLocalDateTime
import ru.woowy.util.randomUUID
import java.time.LocalDateTime

fun randomWorld(
    id: WorldId = randomUUID(),
    characterId: CharacterId = randomUUID(),
    createdAt: LocalDateTime = randomLocalDateTime(),
): World = World(
    id = id,
    characterId = characterId,
    createdAt = createdAt,
)

fun randomWorldRequest(characterId: CharacterId = randomUUID()): WorldRequest = WorldRequest(
    characterId = characterId,
)