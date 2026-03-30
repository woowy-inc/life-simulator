package ru.woowy.infrastructure.messaging

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.woowy.domain.model.WorldTickEvent
import ru.woowy.domain.usecase.NeedUseCase
import ru.woowy.extension.classLogger
import ru.woowy.messaging.KafkaTopic

@Component
class NeedEventListener(
    private val needUseCase: NeedUseCase,
) {
    private val logger = classLogger()

    @KafkaListener(topics = [KafkaTopic.WORLD_TICK_EVENTS])
    suspend fun handleWorldTick(
        @Payload event: WorldTickEvent,
        ack: Acknowledgment,
    ) {
        logger.info("Received world tick event $event")
        needUseCase.processTick(event.characterId, event.tickNumber, event.gameSpeed)
        ack.acknowledge()
    }
}