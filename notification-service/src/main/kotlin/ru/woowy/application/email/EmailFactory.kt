package ru.woowy.application.email

import org.springframework.stereotype.Component
import ru.woowy.application.model.VerifyEmail
import ru.woowy.application.model.WelcomeEmail
import ru.woowy.domain.model.Email
import ru.woowy.domain.model.EmailVerifyEvent
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisteredEvent

@Component
internal class EmailFactory {
    fun getEmail(
        event: Event,
        body: String,
    ): Email = when (event) {
        is UserRegisteredEvent -> WelcomeEmail(event.email, body)
        is EmailVerifyEvent -> VerifyEmail(event.email, body)
    }
}