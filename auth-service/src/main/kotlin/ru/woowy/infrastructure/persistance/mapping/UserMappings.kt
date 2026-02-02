package ru.woowy.infrastructure.persistance.mapping

import ru.woowy.infrastructure.persistance.entity.UserEntity
import ru.woowy.security.User

internal fun UserEntity.asDomain(): User = User(
    id = this.id,
    username = this.username,
    email = this.email,
    password = this.password,
    firstName = this.firstName,
    role = this.role,
)

internal fun User.asEntity(): UserEntity = UserEntity(
    id = this.id,
    username = this.username,
    email = this.email,
    password = this.password,
    firstName = this.firstName,
    role = this.role,
)