package ru.woowy.application.email

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.woowy.domain.model.Email
import ru.woowy.domain.service.EmailService

@Service
@Profile("dev")
class PrinterEmailService : EmailService {
    private val logger = LoggerFactory.getLogger(PrinterEmailService::class.java)

    override fun send(email: Email) {
        logger.info("Sent email: $email")
    }
}