package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.UserRegisterRequestedEvent
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.infrastructure.persistance.entity.UserEntity
import ru.woowy.security.User
import ru.woowy.security.UserDto
import java.util.UUID
import kotlin.time.Clock

internal fun UserEntity.asDomain(): User = User(
    id = this.id,
    username = this.username,
    email = this.email,
    password = this.password,
    firstName = this.firstName,
    role = this.role,
    isEmailVerified = this.isEmailVerified,
)

internal fun User.asEntity(): UserEntity = UserEntity(
    id = this.id,
    username = this.username,
    email = this.email,
    password = this.password,
    firstName = this.firstName,
    role = this.role,
    isEmailVerified = this.isEmailVerified,
)

internal fun User.asDto(): UserDto = UserDto(
    id = this.id,
    username = this.username,
    email = this.email,
    firstName = this.firstName,
    role = this.role,
    isEmailVerified = this.isEmailVerified,
)

internal fun User.asRegisterRequestedEvent(key: String): UserRegisterRequestedEvent = UserRegisterRequestedEvent(
    eventId = UUID.randomUUID().toString(),
    timestamp = Clock.System.now().toEpochMilliseconds(),
    userId = this.id.toString(),
    username = this.username,
    email = this.email,
    firstName = this.firstName,
    key = key,
)

internal fun User.asRegisteredEvent(): UserRegisteredEvent = UserRegisteredEvent(
    eventId = UUID.randomUUID().toString(),
    timestamp = Clock.System.now().toEpochMilliseconds(),
    userId = this.id.toString(),
    username = this.username,
    email = this.email,
    firstName = this.firstName,
    role = this.role,
)