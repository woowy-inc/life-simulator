package ru.woowy.domain.repository

import ru.woowy.domain.model.EmailVerificationKey

internal interface EmailVerificationKeyRepository {
    fun findByKey(key: String): EmailVerificationKey?

    fun save(verificationKey: EmailVerificationKey): EmailVerificationKey
}