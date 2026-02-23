package ru.woowy.application.usecase

import org.slf4j.LoggerFactory
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
    private val logger = LoggerFactory.getLogger(SendEmailUseCase::class.java)

    suspend operator fun invoke(event: Event) {
        try {
            val emailBody = emailBodyResolver.resolve(event)
            val body = renderer.render(emailBody)
            val email = emailFactory.getEmail(event, body)

            logger.info("Sending email to ${email.to}")
            emailService.send(email)

            logger.info("Email sent successfully to ${email.to}")
        } catch (ex: Exception) {
            logger.error("Failed to send email for event $event", ex)
        }
    }
}