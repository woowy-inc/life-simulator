package ru.woowy.application.usecase

import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.domain.model.CacheName
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.model.UsernameAvailableDto
import ru.woowy.domain.repository.UserRepository
import ru.woowy.domain.usecase.EmailVerificationKeyUseCase
import ru.woowy.domain.usecase.UserUseCase
import ru.woowy.extension.unauthorized
import ru.woowy.infrastructure.mapper.asDto
import ru.woowy.infrastructure.mapper.asRegisterRequestedEvent
import ru.woowy.infrastructure.mapper.asRegisteredEvent
import ru.woowy.security.User
import ru.woowy.security.UserDto
import ru.woowy.security.UserId
import ru.woowy.security.UserRole
import java.time.LocalDateTime

@Service
class UserUseCaseImpl(
    private val userRepository: UserRepository,
    private val emailVerificationKeyUseCase: EmailVerificationKeyUseCase,
    private val passwordEncoder: PasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : UserUseCase {
    companion object {
        const val INVALID_VERIFICATION_KEY = "Invalid verification key"
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = [CacheName.USER_BY_USER_ID], key = "#userId")
    override fun getByUserId(userId: UserId): User? = userRepository.findById(userId)

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = [CacheName.USER_BY_USERNAME], key = "#username")
    override fun getByUsername(username: String): User? = userRepository.findByUsername(username)

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = [CacheName.USERNAME_AVAILABILITY_BY_USERNAME], key = "#username")
    override fun isUsernameAvailable(username: String): UsernameAvailableDto {
        val isAvailable = !userRepository.isUsernameExists(username)
        return UsernameAvailableDto(username = username, isAvailable = isAvailable)
    }

    @Transactional
    override fun register(request: UserRegisterRequest): UserDto {
        val hashedPassword = requireNotNull(passwordEncoder.encode(request.password))
        val user = userRepository.add(request.copy(password = hashedPassword))
        val verificationKey = emailVerificationKeyUseCase.add(user)

        applicationEventPublisher.publishEvent(user.asRegisterRequestedEvent(verificationKey.key))

        return user.asDto()
    }

    override fun verifyEmail(key: String): UserDto {
        val now = LocalDateTime.now()
        val key = emailVerificationKeyUseCase.getByKey(key) ?: unauthorized(INVALID_VERIFICATION_KEY)

        if (key.used || key.expiresAt < now) {
            unauthorized(INVALID_VERIFICATION_KEY)
        }

        val user = userRepository.findById(key.user.id) ?: unauthorized(INVALID_VERIFICATION_KEY)

        emailVerificationKeyUseCase.update(key.copy(used = true))
        val updatedUser = userRepository.update(user.copy(role = UserRole.USER, isEmailVerified = true))

        applicationEventPublisher.publishEvent(updatedUser.asRegisteredEvent())

        return updatedUser.asDto()
    }
}