package ru.woowy.user.application.usecase

import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.security.User
import ru.woowy.security.UserDto
import ru.woowy.user.domain.model.EmailVerificationKey
import ru.woowy.user.domain.model.UserRegisterRequest
import ru.woowy.user.domain.repository.EmailVerificationKeyRepository
import ru.woowy.user.domain.repository.UserRepository
import ru.woowy.user.infrastructure.extension.generateSecureHexString
import ru.woowy.user.infrastructure.mapper.asDto
import ru.woowy.user.infrastructure.mapper.asRegisterRequestedEvent
import java.time.LocalDateTime

@Service
@Transactional
internal class UserRegisterUseCase(
    private val userRepository: UserRepository,
    private val emailVerificationKeyRepository: EmailVerificationKeyRepository,
    private val passwordEncoder: PasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    companion object {
        private const val VERIFICATION_KEY_DURATION_HOURS = 24L
    }

    operator fun invoke(request: UserRegisterRequest): UserDto {
        val hashedPassword = requireNotNull(passwordEncoder.encode(request.password))
        val user = userRepository.add(request.copy(password = hashedPassword))
        val verificationKey = addVerificationKey(user)

        applicationEventPublisher.publishEvent(user.asRegisterRequestedEvent(verificationKey.key))

        return user.asDto()
    }

    private fun addVerificationKey(user: User): EmailVerificationKey {
        val key =
            EmailVerificationKey(
                key = generateSecureHexString(),
                user = user,
                expiresAt = LocalDateTime.now().plusHours(VERIFICATION_KEY_DURATION_HOURS),
                used = false,
            )

        return emailVerificationKeyRepository.save(key)
    }
}