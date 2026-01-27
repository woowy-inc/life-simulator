package ru.woowy.infrastructure.messaging

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.woowy.domain.DomainEvent
import ru.woowy.domain.service.WorldService
import ru.woowy.game.KafkaTopic
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.readValue

@Component
class WorldEventConsumerImpl(
    private val mapper: ObjectMapper,
    private val worldService: WorldService,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = [KafkaTopic.WORLD_TICK])
    fun onWorldTick(
        @Payload message: String,
    ) {
        try {
            val event = mapper.readValue<DomainEvent.WorldTickEvent>(message)
            worldService.processWorldTick(event)
        } catch (ex: Exception) {
            logger.error(ex.message, ex)
        }
    }
}