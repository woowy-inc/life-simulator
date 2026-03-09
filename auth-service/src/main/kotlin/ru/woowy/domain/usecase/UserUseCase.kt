package ru.woowy.domain.usecase

import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.model.UsernameAvailableDto
import ru.woowy.id.UserId
import ru.woowy.security.User
import ru.woowy.security.UserDto

interface UserUseCase {
    fun getByUserId(userId: UserId): User?

    fun getByUsername(username: String): User?

    fun isUsernameAvailable(username: String): UsernameAvailableDto

    fun register(request: UserRegisterRequest): UserDto

    fun verifyEmail(key: String): UserDto
}