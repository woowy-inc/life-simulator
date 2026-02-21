package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.application.email.EmailFactory
import ru.woowy.application.email.EmailTemplateRenderer
import ru.woowy.application.email.resolver.EmailBodyResolver
import ru.woowy.domain.model.Event
import ru.woowy.domain.service.EmailService

@Service
internal class SendEmailUseCase(
    private val emailService: EmailService,
    private val emailFactory: EmailFactory,
    private val emailBodyResolver: EmailBodyResolver,
    private val renderer: EmailTemplateRenderer,
) {
    suspend operator fun invoke(event: Event) {
        val emailBody = emailBodyResolver.resolve(event)
        val body = renderer.render(emailBody)
        val email = emailFactory.getEmail(event, body)

        emailService.send(email)
    }
}