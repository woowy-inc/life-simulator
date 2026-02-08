package ru.woowy.application.service

import org.springframework.stereotype.Service
import ru.woowy.application.usecase.GetUserByUserIdUseCase
import ru.woowy.domain.service.UserService
import ru.woowy.extension.notFound
import ru.woowy.infrastructure.mapping.asDto
import ru.woowy.security.UserDto
import java.util.UUID

@Service
internal class UserServiceImpl(
    private val getUserByUserIdUseCase: GetUserByUserIdUseCase,
) : UserService {
    override fun getUser(userId: UUID): UserDto = getUserByUserIdUseCase(userId)
        ?.asDto()
        ?: notFound("User[userId:$userId] not found")
}