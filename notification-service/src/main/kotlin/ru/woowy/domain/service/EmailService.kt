package ru.woowy.domain.service

import ru.woowy.domain.model.Email

internal interface EmailService {
    fun send(email: Email)
}