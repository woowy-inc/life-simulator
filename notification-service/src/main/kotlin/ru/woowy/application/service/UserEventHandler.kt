package ru.woowy.application.service

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.game.KafkaTopic

@Service
internal class UserEventHandler {
    @KafkaListener(topics = [KafkaTopic.USER_EVENTS])
    fun handleUserRegistered(
        @Payload event: UserRegisteredEvent,
        ack: Acknowledgment,
    ) {
        println(event)

        ack.acknowledge()
    }
}