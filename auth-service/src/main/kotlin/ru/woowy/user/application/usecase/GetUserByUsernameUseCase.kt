package ru.woowy.user.application.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.security.User
import ru.woowy.user.domain.repository.UserRepository

@Service
@Transactional
internal class GetUserByUsernameUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(username: String): User? = userRepository.findByUsername(username)
}