package ru.woowy.domain.model

import ru.woowy.id.CharacterId

data class CharacterKey(
    val characterId: CharacterId,
    val tickNumber: Long,
)