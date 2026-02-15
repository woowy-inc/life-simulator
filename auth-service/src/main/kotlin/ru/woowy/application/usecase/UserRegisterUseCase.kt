package ru.woowy.application.usecase

import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.domain.repository.UserRepository
import ru.woowy.extension.internalError
import ru.woowy.infrastructure.mapping.asDto
import ru.woowy.infrastructure.mapping.asEvent
import ru.woowy.security.User
import ru.woowy.security.UserDto
import java.time.Instant
import java.util.UUID

@Service
@Transactional
internal class UserRegisterUseCase(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    operator fun invoke(request: UserRegisterRequest): UserDto {
        val hashedPassword =
            passwordEncoder.encode(request.password) ?: internalError("Encode password error")

        val user = userRepository.addUser(request.copy(password = hashedPassword))
        publishEvent(user)

        return user.asDto()
    }

    private fun publishEvent(user: User) {
        applicationEventPublisher.publishEvent(
            UserRegisteredEvent(
                eventId = UUID.randomUUID().toString(),
                timestamp = Instant.now().toEpochMilli(),
                user = user.asEvent(),
            ),
        )
    }
}