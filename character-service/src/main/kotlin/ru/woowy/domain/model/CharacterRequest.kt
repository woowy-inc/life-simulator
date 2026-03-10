package ru.woowy.domain.model

import ru.woowy.character.Gender
import ru.woowy.id.LocationId

data class CharacterRequest(
    val name: String,
    val gender: Gender,
    val locationId: LocationId,
)