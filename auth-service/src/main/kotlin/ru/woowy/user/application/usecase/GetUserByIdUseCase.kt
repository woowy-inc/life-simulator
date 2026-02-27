package ru.woowy.user.application.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.security.UserDto
import ru.woowy.user.domain.repository.UserRepository
import ru.woowy.user.infrastructure.mapper.asDto
import java.util.UUID

@Service
@Transactional
internal class GetUserByIdUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(userId: UUID): UserDto? = userRepository.findById(userId)?.asDto()
}