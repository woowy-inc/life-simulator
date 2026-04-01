package ru.woowy.domain.model

import kotlinx.serialization.Contextual
import ru.woowy.id.CharacterId

interface CharacterStateEvent : TickableEvent {
    @Contextual
    val characterId: CharacterId
}