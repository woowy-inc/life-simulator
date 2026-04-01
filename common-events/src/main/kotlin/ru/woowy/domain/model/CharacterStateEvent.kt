package ru.woowy.domain.model

import kotlinx.serialization.Contextual
import ru.woowy.id.CharacterId

interface CharacterStateEvent : Event {
    @Contextual
    val characterId: CharacterId
}