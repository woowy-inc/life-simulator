package ru.woowy.infrastructure.mapping

import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.infrastructure.persistance.entity.UserEntity
import ru.woowy.security.UserRole

internal fun UserRegisterRequest.asEntity(): UserEntity = UserEntity(
    username = this.username,
    email = this.email,
    password = this.password,
    firstName = this.firstName,
    role = UserRole.USER,
)