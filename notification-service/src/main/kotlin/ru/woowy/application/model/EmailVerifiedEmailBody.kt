package ru.woowy.application.model

import ru.woowy.domain.model.EmailBody

internal data class EmailVerifiedEmailBody(
    private val firstName: String,
    private val username: String,
    private val email: String,
    private val frontendUrl: String,
) : EmailBody() {
    override val templateName = "email/verified-email"
    override val variables =
        mapOf(
            "firstName" to firstName,
            "username" to username,
            "email" to email,
            "loginUrl" to "$frontendUrl/login",
        )
}