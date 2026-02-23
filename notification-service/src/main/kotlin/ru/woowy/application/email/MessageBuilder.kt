package ru.woowy.application.email

import jakarta.mail.internet.MimeMessage
import ru.woowy.domain.model.Email

internal interface MessageBuilder {
    fun build(email: Email): MimeMessage
}