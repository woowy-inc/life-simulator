package ru.woowy.domain.model

data class VerifyEmail(
    override val to: String,
    override val body: String,
    override val isHtml: Boolean = true,
) : Email() {
    override val subject: String
        get() = "Подтвердите ваш email — Woowy"
}