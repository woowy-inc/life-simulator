package ru.woowy.application.event

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import ru.woowy.application.usecase.SendEmailUseCase
import ru.woowy.domain.model.Event
import ru.woowy.messaging.KafkaTopic

@Service
internal class UserEventHandler(
    private val sendEmailUseCase: SendEmailUseCase,
) {
    private val logger = LoggerFactory.getLogger(UserEventHandler::class.java)

    @KafkaListener(topics = [KafkaTopic.USER_EVENTS])
    suspend fun handleUserEvent(
        @Payload event: Event,
        ack: Acknowledgment,
    ) {
        logger.info("Received $event")
        sendEmailUseCase(event)
        ack.acknowledge()
    }
}