package ru.woowy.domain.usecase

import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.model.UsernameAvailableDto
import ru.woowy.security.User
import ru.woowy.security.UserDto
import ru.woowy.security.UserId

interface UserUseCase {
    fun getByUserId(userId: UserId): User?

    fun getByUsername(username: String): User?

    fun isUsernameAvailable(username: String): UsernameAvailableDto

    fun register(request: UserRegisterRequest): UserDto

    fun verifyEmail(key: String): UserDto
}