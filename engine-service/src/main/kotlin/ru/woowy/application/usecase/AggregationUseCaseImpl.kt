package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.buffer.ThresholdAggregator
import ru.woowy.domain.model.CharacterStateEvent
import ru.woowy.domain.usecase.AggregationUseCase
import ru.woowy.domain.websocket.WebSocketPublisher
import ru.woowy.id.CharacterId

@Service
class AggregationUseCaseImpl(
    private val aggregator: ThresholdAggregator,
    private val webSocketPublisher: WebSocketPublisher,
) : AggregationUseCase {
    override suspend fun processEvent(
        characterId: CharacterId,
        event: CharacterStateEvent,
    ) {
        aggregator.threshold(characterId, event) { state ->
            webSocketPublisher.publish(characterId, state)
        }
    }
}