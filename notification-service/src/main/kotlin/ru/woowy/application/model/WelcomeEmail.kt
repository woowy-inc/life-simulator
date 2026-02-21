package ru.woowy.application.model

import ru.woowy.domain.model.Email

internal data class WelcomeEmail(
    override val to: String,
    override val body: String,
    override val isHtml: Boolean = true,
) : Email() {
    override val subject: String
        get() = "Добро пожаловать в Woowy!"
}