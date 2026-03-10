package ru.woowy.domain.model

import ru.woowy.character.Gender
import ru.woowy.id.CharacterId
import ru.woowy.id.LocationId
import ru.woowy.id.UserId
import ru.woowy.id.WorldId
import java.time.LocalDateTime

data class Character(
    val id: CharacterId,
    val userId: UserId,
    val name: String,
    val gender: Gender,
    val birthday: LocalDateTime,
    val locationId: LocationId,
    val worldId: WorldId? = null,
    val createdAt: LocalDateTime,
)