package ru.woowy.application.email.resolver

import org.springframework.stereotype.Component
import ru.woowy.application.config.AppProperties
import ru.woowy.application.model.EmailVerifiedEmailBody
import ru.woowy.application.model.VerifyEmailBody
import ru.woowy.domain.model.EmailBody
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisterRequestedEvent
import ru.woowy.domain.model.UserRegisteredEvent

@Component
internal class EmailBodyResolver(
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