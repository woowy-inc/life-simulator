package ru.woowy.domain.repository

import ru.woowy.domain.model.EmailVerificationKey

interface EmailVerificationKeyRepository {
    fun findByKey(key: String): EmailVerificationKey?

    fun save(verificationKey: EmailVerificationKey): EmailVerificationKey
}