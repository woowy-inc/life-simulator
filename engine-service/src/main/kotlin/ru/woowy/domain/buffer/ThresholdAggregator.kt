package ru.woowy.domain.buffer

import ru.woowy.domain.model.CharacterState
import ru.woowy.domain.model.TickableEvent
import ru.woowy.id.CharacterId

interface ThresholdAggregator {
    suspend fun threshold(
        characterId: CharacterId,
        event: TickableEvent,
        onRelease: (CharacterState) -> Unit,
    )
}