package ru.woowy.application.email.resolver

import org.springframework.stereotype.Component
import ru.woowy.application.config.AppProperties
import ru.woowy.application.model.VerifyEmailBody
import ru.woowy.application.model.WelcomeEmailBody
import ru.woowy.domain.model.EmailBody
import ru.woowy.domain.model.EmailVerifyEvent
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisteredEvent

@Component
internal class EmailBodyResolver(
    private val appProperties: AppProperties,
) {
    fun resolve(event: Event): EmailBody = when (event) {
        is UserRegisteredEvent -> {
            WelcomeEmailBody(
                firstName = event.firstName,
                username = event.username,
                email = event.email,
                frontendUrl = appProperties.frontendUrl,
            )
        }

        is EmailVerifyEvent -> {
            VerifyEmailBody(
                firstName = event.firstName,
                token = event.token,
                frontendUrl = appProperties.frontendUrl,
            )
        }
    }
}