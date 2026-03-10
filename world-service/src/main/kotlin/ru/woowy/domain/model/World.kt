package ru.woowy.domain.model

import ru.woowy.id.CharacterId
import ru.woowy.id.WorldId
import java.time.LocalDateTime

data class World(
    val id: WorldId,
    val characterId: CharacterId,
    val createdAt: LocalDateTime,
)