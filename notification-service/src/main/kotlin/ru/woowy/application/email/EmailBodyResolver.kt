package ru.woowy.application.email

import org.springframework.stereotype.Component
import ru.woowy.domain.model.EmailBody
import ru.woowy.domain.model.EmailVerifiedEmailBody
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisterRequestedEvent
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.domain.model.VerifyEmailBody
import ru.woowy.infrastructure.config.AppProperties

@Component
class EmailBodyResolver(
    private val appProperties: AppProperties,
) {
    fun resolve(event: Event): EmailBody = when (event) {
        is UserRegisterRequestedEvent -> {
            VerifyEmailBody(
                firstName = event.firstName,
                key = event.key,
                frontendUrl = appProperties.frontendUrl,
            )
        }

        is UserRegisteredEvent -> {
            EmailVerifiedEmailBody(
                firstName = event.firstName,
                username = event.username,
                email = event.email,
                frontendUrl = appProperties.frontendUrl,
            )
        }
    }
}