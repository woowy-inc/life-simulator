package ru.woowy.application.usecase

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.woowy.application.email.EmailBodyResolver
import ru.woowy.application.email.EmailFactory
import ru.woowy.application.email.EmailTemplateRenderer
import ru.woowy.domain.model.Event
import ru.woowy.domain.service.EmailService
import ru.woowy.domain.usecase.EmailUseCase

@Service
class EmailUseCaseImpl(
    private val emailService: EmailService,
    private val emailFactory: EmailFactory,
    private val emailBodyResolver: EmailBodyResolver,
    private val renderer: EmailTemplateRenderer,
) : EmailUseCase {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun send(event: Event) {
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