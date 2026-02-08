package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.repository.UserRepository
import ru.woowy.security.User
import java.util.UUID

@Service
internal class GetUserByUserIdUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(userId: UUID): User? = userRepository.findById(userId)
}