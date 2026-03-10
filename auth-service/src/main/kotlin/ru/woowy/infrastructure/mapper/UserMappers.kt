package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.UserRegisterRequestedEvent
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.infrastructure.persistence.entity.UserEntity
import ru.woowy.security.User
import ru.woowy.security.UserDto
import java.util.UUID
import kotlin.time.Clock

fun UserEntity.asDomain(): User = User(
    id = this.id,
    username = this.username,
    email = this.email,
    password = this.password,
    firstName = this.firstName,
    role = this.role,
    isEmailVerified = this.isEmailVerified,
)

fun User.asEntity(): UserEntity = UserEntity(
    id = this.id,
    username = this.username,
    email = this.email,
    password = this.password,
    firstName = this.firstName,
    role = this.role,
    isEmailVerified = this.isEmailVerified,
)

fun User.asDto(): UserDto = UserDto(
    id = this.id,
    username = this.username,
    email = this.email,
    firstName = this.firstName,
    role = this.role,
    isEmailVerified = this.isEmailVerified,
)

fun User.asRegisterRequestedEvent(key: String): UserRegisterRequestedEvent = UserRegisterRequestedEvent(
    eventId = UUID.randomUUID(),
    timestamp = Clock.System.now().toEpochMilliseconds(),
    userId = this.id,
    username = this.username,
    email = this.email,
    firstName = this.firstName,
    key = key,
)

fun User.asRegisteredEvent(): UserRegisteredEvent = UserRegisteredEvent(
    eventId = UUID.randomUUID(),
    timestamp = Clock.System.now().toEpochMilliseconds(),
    userId = this.id,
    username = this.username,
    email = this.email,
    firstName = this.firstName,
    role = this.role,
)