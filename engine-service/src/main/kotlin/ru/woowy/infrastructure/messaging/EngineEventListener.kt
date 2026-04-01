package ru.woowy.infrastructure.messaging

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.woowy.domain.model.CharacterDeletedEvent
import ru.woowy.domain.model.CharacterStateEvent
import ru.woowy.domain.usecase.AggregationUseCase
import ru.woowy.domain.usecase.GameSessionUseCase
import ru.woowy.messaging.KafkaTopic

@Component
class EngineEventListener(
    private val aggregationUseCase: AggregationUseCase,
    private val gameSessionUseCase: GameSessionUseCase,
) {
    @KafkaListener(topics = [KafkaTopic.CHARACTER_STATE_EVENTS])
    suspend fun handleCharacterState(
        @Payload event: CharacterStateEvent,
        ack: Acknowledgment,
    ) {
        aggregationUseCase.processEvent(event.characterId, event)
        ack.acknowledge()
    }

    @KafkaListener(topics = [KafkaTopic.CHARACTER_EVENTS])
    suspend fun handleCharacterDeleted(
        @Payload event: CharacterDeletedEvent,
        ack: Acknowledgment,
    ) {
        gameSessionUseCase.delete(event.characterId)
        ack.acknowledge()
    }
}