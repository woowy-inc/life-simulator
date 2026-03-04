package ru.woowy.domain.service

import ru.woowy.domain.model.Email

interface EmailService {
    fun send(email: Email)
}