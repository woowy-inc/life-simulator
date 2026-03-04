package ru.woowy.domain.model

data class VerifiedEmail(
    override val to: String,
    override val body: String,
    override val isHtml: Boolean = true,
) : Email() {
    override val subject: String
        get() = "Добро пожаловать в Woowy!"
}