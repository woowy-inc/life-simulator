package ru.woowy.infrastructure.mapping

import ru.woowy.domain.model.UserEvent
import ru.woowy.infrastructure.persistance.entity.UserEntity
import ru.woowy.security.User
import ru.woowy.security.UserDto

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

internal fun User.asDto(): UserDto = UserDto(
    id = this.id,
    username = this.username,
    email = this.email,
    firstName = this.firstName,
    role = this.role,
)

internal fun User.asEvent(): UserEvent = UserEvent(
    id = this.id.toString(),
    username = this.username,
    email = this.email,
    firstName = this.firstName,
    role = this.role.toString(),
)