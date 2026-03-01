package ru.woowy.user.infrastructure.persistence.adapter

import org.springframework.stereotype.Component
import ru.woowy.user.domain.model.EmailVerificationKey
import ru.woowy.user.domain.repository.EmailVerificationKeyRepository
import ru.woowy.user.infrastructure.mapper.asDomain
import ru.woowy.user.infrastructure.mapper.asEntity
import ru.woowy.user.infrastructure.persistence.repository.CrudEmailVerificationKeyRepository

@Component
internal class EmailVerificationKeyAdapter(
    private val emailVerificationTokenRepository: CrudEmailVerificationKeyRepository,
) : EmailVerificationKeyRepository {
    override fun findByKey(key: String): EmailVerificationKey? =
        emailVerificationTokenRepository.findByKey(key)?.asDomain()

    override fun save(verificationKey: EmailVerificationKey): EmailVerificationKey =
        emailVerificationTokenRepository.save(verificationKey.asEntity()).asDomain()
}