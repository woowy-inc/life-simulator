package ru.woowy.domain.buffer

import ru.woowy.domain.model.CharacterState
import ru.woowy.domain.model.CharacterStateEvent
import ru.woowy.id.CharacterId

interface ThresholdAggregator {
    suspend fun threshold(
        characterId: CharacterId,
        event: CharacterStateEvent,
        onRelease: (CharacterState) -> Unit,
    )
}