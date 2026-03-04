package ru.woowy.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.EmailVerificationKey
import ru.woowy.domain.repository.EmailVerificationKeyRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.mapper.asEntity
import ru.woowy.infrastructure.persistence.jpa.JpaEmailVerificationKeyRepository

@Repository
class EmailVerificationKeyRepositoryImpl(
    private val jpaEmailVerificationKeyRepository: JpaEmailVerificationKeyRepository,
) : EmailVerificationKeyRepository {
    override fun findByKey(key: String): EmailVerificationKey? =
        jpaEmailVerificationKeyRepository.findByKey(key)?.asDomain()

    override fun save(verificationKey: EmailVerificationKey): EmailVerificationKey =
        jpaEmailVerificationKeyRepository.save(verificationKey.asEntity()).asDomain()
}