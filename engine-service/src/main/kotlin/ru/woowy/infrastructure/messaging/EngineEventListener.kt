package ru.woowy.infrastructure.messaging

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.woowy.domain.model.CharacterStateEvent
import ru.woowy.domain.usecase.AggregationUseCase
import ru.woowy.extension.classLogger
import ru.woowy.messaging.KafkaTopic

@Component
class EngineEventListener(
    private val aggregationUseCase: AggregationUseCase,
) {
    private val logger = classLogger()

    @KafkaListener(topics = [KafkaTopic.CHARACTER_STATE_EVENTS])
    suspend fun handleCharacterState(
        @Payload event: CharacterStateEvent,
        ack: Acknowledgment,
    ) {
        logger.info("Received world created event $event")
        aggregationUseCase.processEvent(event.characterId, event)
        ack.acknowledge()
    }
}