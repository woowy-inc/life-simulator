package ru.woowy.user.domain.repository

import ru.woowy.security.User
import ru.woowy.user.domain.model.UserRegisterRequest
import java.util.UUID

internal interface UserRepository {
    fun isUsernameExists(username: String): Boolean

    fun findByUsername(username: String): User?

    fun findById(userId: UUID): User?

    fun add(request: UserRegisterRequest): User

    fun update(user: User): User
}