package ru.woowy.domain.usecase

import ru.woowy.domain.model.EmailVerificationKey
import ru.woowy.security.User

interface EmailVerificationKeyUseCase {
    fun add(user: User): EmailVerificationKey

    fun update(key: EmailVerificationKey): EmailVerificationKey?

    fun getByKey(key: String): EmailVerificationKey?
}