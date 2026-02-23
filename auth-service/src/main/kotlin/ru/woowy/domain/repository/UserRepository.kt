package ru.woowy.domain.repository

import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.security.User
import java.util.UUID

internal interface UserRepository {
    fun isUsernameExists(username: String): Boolean

    fun findByUsername(username: String): User?

    fun findById(userId: UUID): User?

    fun add(request: UserRegisterRequest): User

    fun update(user: User): User
}