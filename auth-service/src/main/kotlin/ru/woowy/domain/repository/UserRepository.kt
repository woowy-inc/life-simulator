package ru.woowy.domain.repository

import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.security.User
import java.util.UUID

internal interface UserRepository {
    fun findByUsername(username: String): User?

    fun findById(userId: UUID): User?

    fun addUser(request: UserRegisterRequest): User
}