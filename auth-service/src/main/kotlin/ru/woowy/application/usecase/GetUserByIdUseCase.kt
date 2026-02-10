package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.domain.repository.UserRepository
import ru.woowy.infrastructure.mapping.asDto
import ru.woowy.security.UserDto
import java.util.UUID

@Service
@Transactional
internal class GetUserByIdUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(userId: UUID): UserDto? = userRepository.findById(userId)?.asDto()
}