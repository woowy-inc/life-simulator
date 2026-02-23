package ru.woowy.application.email

import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import ru.woowy.application.config.AppProperties
import ru.woowy.domain.model.Email

@Component
internal class MimeMessageBuilder(
    private val mailSender: JavaMailSender,
    private val appProperties: AppProperties,
) : MessageBuilder {
    override fun build(email: Email): MimeMessage {
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