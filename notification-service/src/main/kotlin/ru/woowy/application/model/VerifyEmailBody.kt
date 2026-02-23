package ru.woowy.application.model

import ru.woowy.domain.model.EmailBody

internal data class VerifyEmailBody(
    private val firstName: String,
    private val key: String,
    private val frontendUrl: String,
) : EmailBody() {
    override val templateName = "email/verify-email"
    override val variables =
        mapOf(
            "firstName" to firstName,
            "verifyUrl" to "$frontendUrl/verify?key=$key",
        )
}