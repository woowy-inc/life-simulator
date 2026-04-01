package ru.woowy.domain.usecase

import ru.woowy.domain.model.TickableEvent
import ru.woowy.id.CharacterId

interface AggregationUseCase {
    suspend fun processEvent(
        characterId: CharacterId,
        event: TickableEvent,
    )
}