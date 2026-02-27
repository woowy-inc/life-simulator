package ru.woowy.user.application.usecase

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.extension.unauthorized
import ru.woowy.security.UserDto
import ru.woowy.security.UserRole
import ru.woowy.user.domain.repository.EmailVerificationKeyRepository
import ru.woowy.user.domain.repository.UserRepository
import ru.woowy.user.infrastructure.mapper.asDto
import ru.woowy.user.infrastructure.mapper.asRegisteredEvent
import java.time.LocalDateTime

@Service
@Transactional
internal class VerifyUserEmailUseCase(
    private val emailVerificationKeyRepository: EmailVerificationKeyRepository,
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    companion object {
        const val ERROR_MESSAGE = "Invalid verification key"
    }

    operator fun invoke(key: String): UserDto {
        val now = LocalDateTime.now()
        val key = emailVerificationKeyRepository.findByKey(key) ?: unauthorized(ERROR_MESSAGE)

        if (key.used || key.expiresAt < now) {
            unauthorized(ERROR_MESSAGE)
        }

        val user = userRepository.findById(key.user.id) ?: unauthorized(ERROR_MESSAGE)

        emailVerificationKeyRepository.save(key.copy(used = true))
        val updatedUser = userRepository.update(user.copy(role = UserRole.USER, isEmailVerified = true))

        applicationEventPublisher.publishEvent(updatedUser.asRegisteredEvent())

        return updatedUser.asDto()
    }
}