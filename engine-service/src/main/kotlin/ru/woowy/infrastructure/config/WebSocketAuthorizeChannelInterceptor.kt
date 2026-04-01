package ru.woowy.infrastructure.config

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageDeliveryException
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.stereotype.Component

@Component
class WebSocketAuthorizeChannelInterceptor(
    private val jwtDecoder: JwtDecoder,
) : ChannelInterceptor {
    override fun preSend(
        message: Message<*>,
        channel: MessageChannel,
    ): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)

        if (accessor.command == StompCommand.CONNECT) {
            val token =
                accessor.getFirstNativeHeader("Authorization")
                    ?: throw MessageDeliveryException("Missing Authorization header")

            val jwt =
                try {
                    jwtDecoder.decode(token)
                } catch (ex: JwtException) {
                    throw MessageDeliveryException("Invalid token: ${ex.message}")
                }

            accessor.sessionAttributes?.put("userId", jwt.subject)
        }

        return message
    }
}