package ru.woowy.application.email

import org.springframework.context.annotation.Profile
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import ru.woowy.domain.model.Email
import ru.woowy.domain.service.EmailService

@Service
@Profile("prod")
class EmailServiceImpl(
    private val mailSender: JavaMailSender,
    private val messageBuilder: MessageBuilder,
) : EmailService {
    override fun send(email: Email) {
        val message = messageBuilder.build(email)
        mailSender.send(message)
    }
}