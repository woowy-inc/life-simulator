package ru.woowy.infrastructure.websocket

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import ru.woowy.domain.model.CharacterState
import ru.woowy.domain.websocket.WebSocketPublisher
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.config.WebSocketTopic

@Component
class WebSocketPublisherImpl(
    private val simpMessagingTemplate: SimpMessagingTemplate,
) : WebSocketPublisher {
    override fun publish(
        characterId: CharacterId,
        state: CharacterState,
    ) {
        val topic = WebSocketTopic.CHARACTER_TOPIC_PREFIX + "/$characterId"
        simpMessagingTemplate.convertAndSend(topic, state)
    }
}