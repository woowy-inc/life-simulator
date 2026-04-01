package ru.woowy.domain.usecase

import ru.woowy.domain.model.CharacterStateEvent
import ru.woowy.id.CharacterId

interface AggregationUseCase {
    suspend fun processEvent(
        characterId: CharacterId,
        event: CharacterStateEvent,
    )
}