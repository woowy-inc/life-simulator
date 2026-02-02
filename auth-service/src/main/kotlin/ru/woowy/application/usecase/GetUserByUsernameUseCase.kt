package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.infrastructure.persistance.UserRepository
import ru.woowy.infrastructure.persistance.mapping.asDomain
import ru.woowy.security.User

@Service
internal class GetUserByUsernameUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(username: String): User? = userRepository.findUserByUsername(username)?.asDomain()
}