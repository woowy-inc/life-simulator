package ru.woowy.user.infrastructure.mapper

import ru.woowy.security.UserRole
import ru.woowy.user.domain.model.UserRegisterRequest
import ru.woowy.user.infrastructure.persistance.entity.UserEntity

internal fun UserRegisterRequest.asEntity(): UserEntity = UserEntity(
    username = this.username,
    email = this.email,
    password = this.password,
    firstName = this.firstName,
    role = UserRole.PENDING,
    isEmailVerified = false,
)