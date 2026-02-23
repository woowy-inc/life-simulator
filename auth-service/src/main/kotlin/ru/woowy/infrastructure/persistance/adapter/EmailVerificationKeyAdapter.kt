package ru.woowy.infrastructure.persistance.adapter

import org.springframework.stereotype.Component
import ru.woowy.domain.model.EmailVerificationKey
import ru.woowy.domain.repository.EmailVerificationKeyRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.mapper.asEntity
import ru.woowy.infrastructure.persistance.repository.CrudEmailVerificationKeyRepository

@Component
internal class EmailVerificationKeyAdapter(
    private val emailVerificationTokenRepository: CrudEmailVerificationKeyRepository,
) : EmailVerificationKeyRepository {
    override fun findByKey(key: String): EmailVerificationKey? =
        emailVerificationTokenRepository.findByKey(key)?.asDomain()

    override fun save(verificationKey: EmailVerificationKey): EmailVerificationKey =
        emailVerificationTokenRepository.save(verificationKey.asEntity()).asDomain()
}