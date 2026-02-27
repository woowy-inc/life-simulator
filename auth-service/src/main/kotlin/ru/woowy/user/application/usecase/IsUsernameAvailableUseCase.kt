package ru.woowy.user.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.user.domain.model.UsernameAvailableDto
import ru.woowy.user.domain.repository.UserRepository

@Service
internal class IsUsernameAvailableUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(username: String): UsernameAvailableDto {
        val isAvailable = !userRepository.isUsernameExists(username)

        return UsernameAvailableDto(username = username, isAvailable = isAvailable)
    }
}