package ru.woowy.application.usecase

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.repository.UserRepository
import ru.woowy.extension.internalError
import ru.woowy.infrastructure.mapping.asDto
import ru.woowy.security.UserDto

@Service
@Transactional
internal class UserRegisterUseCase(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    operator fun invoke(request: UserRegisterRequest): UserDto {
        val hashedPassword =
            passwordEncoder
                .encode(request.password)
                ?: internalError("Encode password error")

        return userRepository.addUser(request.copy(password = hashedPassword)).asDto()
    }
}