package ru.woowy.domain.websocket

import ru.woowy.domain.model.CharacterState
import ru.woowy.id.CharacterId

interface WebSocketPublisher {
    fun publish(
        characterId: CharacterId,
        state: CharacterState,
    )
}