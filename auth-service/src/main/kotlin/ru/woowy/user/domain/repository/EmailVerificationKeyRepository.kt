package ru.woowy.user.domain.repository

import ru.woowy.user.domain.model.EmailVerificationKey

internal interface EmailVerificationKeyRepository {
    fun findByKey(key: String): EmailVerificationKey?

    fun save(verificationKey: EmailVerificationKey): EmailVerificationKey
}