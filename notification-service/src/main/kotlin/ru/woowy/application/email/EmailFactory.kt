package ru.woowy.application.email

import org.springframework.stereotype.Component
import ru.woowy.domain.model.Email
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisterRequestedEvent
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.domain.model.VerifiedEmail
import ru.woowy.domain.model.VerifyEmail
import ru.woowy.infrastructure.exception.NoOpEventException

@Component
class EmailFactory {
    fun getEmail(
        event: Event,
        body: String,
    ): Email = when (event) {
        is UserRegisterRequestedEvent -> VerifyEmail(event.email, body)
        is UserRegisteredEvent -> VerifiedEmail(event.email, body)
        else -> throw NoOpEventException("Event: $event")
    }
}