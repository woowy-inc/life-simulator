package ru.woowy.application.event

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import ru.woowy.domain.model.Event
import ru.woowy.domain.usecase.EmailUseCase
import ru.woowy.messaging.KafkaTopic

@Service
class UserEventHandler(
    private val emailUseCase: EmailUseCase,
) {
    private val logger = LoggerFactory.getLogger(UserEventHandler::class.java)

    @KafkaListener(topics = [KafkaTopic.USER_EVENTS])
    suspend fun handleUserEvent(
        @Payload event: Event,
        ack: Acknowledgment,
    ) {
        logger.info("Received $event")
        emailUseCase.send(event)
        ack.acknowledge()
    }
}