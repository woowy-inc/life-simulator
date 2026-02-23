package ru.woowy.application.email

import org.springframework.stereotype.Component
import ru.woowy.application.model.VerifiedEmail
import ru.woowy.application.model.VerifyEmail
import ru.woowy.domain.model.Email
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisterRequestedEvent
import ru.woowy.domain.model.UserRegisteredEvent

@Component
internal class EmailFactory {
    fun getEmail(
        event: Event,
        body: String,
    ): Email = when (event) {
        is UserRegisterRequestedEvent -> VerifyEmail(event.email, body)
        is UserRegisteredEvent -> VerifiedEmail(event.email, body)
    }
}