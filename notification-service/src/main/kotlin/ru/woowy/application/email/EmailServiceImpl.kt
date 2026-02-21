package ru.woowy.application.email

import jakarta.mail.internet.MimeMessage
import org.springframework.context.annotation.Profile
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import ru.woowy.application.config.AppProperties
import ru.woowy.domain.model.Email
import ru.woowy.domain.service.EmailService

@Service
@Profile("prod")
internal class EmailServiceImpl(
    private val mailSender: JavaMailSender,
    private val appProperties: AppProperties,
) : EmailService {
    override fun send(email: Email) {
        val message = getMimeMessage(email)
        mailSender.send(message)
    }

    private fun getMimeMessage(email: Email): MimeMessage {
        val message = mailSender.createMimeMessage()

        MimeMessageHelper(message, true, ENCODING).apply {
            setFrom(appProperties.mail.from, appProperties.mail.fromName)
            setTo(email.to)
            setSubject(email.subject)
            setText(email.body, email.isHtml)
        }

        return message
    }

    companion object {
        private const val ENCODING = "UTF-8"
    }
}