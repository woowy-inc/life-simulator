package ru.woowy.domain.service

import ru.woowy.security.UserDto
import java.util.UUID

internal interface UserService {
    fun getUser(userId: UUID): UserDto
}