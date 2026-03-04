package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.infrastructure.persistence.entity.UserEntity
import ru.woowy.security.UserRole
import java.util.UUID

fun UserRegisterRequest.asEntity(): UserEntity = UserEntity(
    id = UUID.randomUUID(),
    username = this.username,
    email = this.email,
    password = this.password,
    firstName = this.firstName,
    role = UserRole.PENDING,
    isEmailVerified = false,
)