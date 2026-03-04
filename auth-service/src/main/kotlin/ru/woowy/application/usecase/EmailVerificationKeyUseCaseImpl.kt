package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.model.EmailVerificationKey
import ru.woowy.domain.repository.EmailVerificationKeyRepository
import ru.woowy.domain.usecase.EmailVerificationKeyUseCase
import ru.woowy.infrastructure.extension.generateSecureHexString
import ru.woowy.security.User
import java.time.LocalDateTime

@Service
class EmailVerificationKeyUseCaseImpl(
    private val emailVerificationKeyRepository: EmailVerificationKeyRepository,
) : EmailVerificationKeyUseCase {
    companion object {
        private const val VERIFICATION_KEY_DURATION_HOURS = 24L
    }

    override fun add(user: User): EmailVerificationKey {
        val key =
            EmailVerificationKey(
                key =
                    _root_ide_package_.ru.woowy.infrastructure.extension
                        .generateSecureHexString(),
                user = user,
                expiresAt = LocalDateTime.now().plusHours(VERIFICATION_KEY_DURATION_HOURS),
                used = false,
            )

        return emailVerificationKeyRepository.save(key)
    }

    override fun update(key: EmailVerificationKey): EmailVerificationKey? = emailVerificationKeyRepository.save(key)

    override fun getByKey(key: String): EmailVerificationKey? = emailVerificationKeyRepository.findByKey(key)
}