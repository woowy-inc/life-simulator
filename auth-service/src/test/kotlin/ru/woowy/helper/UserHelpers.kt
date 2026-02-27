package ru.woowy.helper

import ru.woowy.auth.domain.model.RefreshTokenRequest
import ru.woowy.auth.domain.model.Token
import ru.woowy.auth.domain.model.TokenDto
import ru.woowy.security.User
import ru.woowy.security.UserRole
import ru.woowy.user.domain.model.EmailVerificationKey
import ru.woowy.user.domain.model.UserRegisterRequest
import ru.woowy.user.domain.model.UsernameAvailableDto
import ru.woowy.user.domain.model.UsernameRequest
import ru.woowy.util.randomBoolean
import ru.woowy.util.randomEmail
import ru.woowy.util.randomLocalDateTime
import ru.woowy.util.randomLong
import ru.woowy.util.randomPassword
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID
import ru.woowy.util.randomUsername
import java.time.LocalDateTime
import java.util.UUID

internal fun randomUser(request: UserRegisterRequest = randomUserRegisterRequest()): User = User(
    id = randomUUID(),
    username = request.username,
    email = request.email,
    password = request.password,
    firstName = request.firstName,
    role = UserRole.USER,
    isEmailVerified = randomBoolean(),
)

internal fun randomUser(
    id: UUID = randomUUID(),
    username: String = randomUsername(),
    email: String = randomEmail(),
    password: String = randomPassword(),
    firstName: String = randomString(),
    role: UserRole = UserRole.USER,
    isEmailVerified: Boolean = randomBoolean(),
): User = User(
    id = id,
    username = username,
    email = email,
    password = password,
    firstName = firstName,
    role = role,
    isEmailVerified = isEmailVerified,
)

internal fun randomUserRegisterRequest(
    username: String = randomUsername(),
    email: String = randomEmail(),
    password: String = randomPassword(),
    firstName: String = randomString(),
): UserRegisterRequest = UserRegisterRequest(
    username = username,
    email = email,
    password = password,
    firstName = firstName,
)

internal fun randomUsernameRequest(
    username: String = randomUsername(),
    password: String = randomPassword(),
): UsernameRequest = UsernameRequest(
    username = username,
    password = password,
)

internal fun randomEmailVerificationKey(
    key: String = randomString(),
    user: User = randomUser(),
    expiresAt: LocalDateTime = randomLocalDateTime(),
    used: Boolean = randomBoolean(),
): EmailVerificationKey = EmailVerificationKey(
    key = key,
    user = user,
    expiresAt = expiresAt,
    used = used,
)

internal fun randomToken(
    value: String = randomString(),
    expiration: Long = randomLong(min = 2_000_000, max = 3_000_000),
): Token = Token(
    value = value,
    expiration = expiration,
)

internal fun randomTokenDto(
    accessToken: Token = randomToken(),
    refreshToken: Token = randomToken(),
): TokenDto = TokenDto(
    accessToken = accessToken.value,
    accessTokenExpiresIn = accessToken.expiration,
    refreshToken = refreshToken.value,
    refreshTokenExpiresIn = refreshToken.expiration,
)

internal fun randomTokenDto(
    accessToken: String = randomString(),
    accessTokenExpiresIn: Long = randomLong(min = 2_000_000, max = 3_000_000),
    refreshToken: String = randomString(),
    refreshTokenExpiresIn: Long = randomLong(min = 2_000_000, max = 3_000_000),
): TokenDto = TokenDto(
    accessToken = accessToken,
    accessTokenExpiresIn = accessTokenExpiresIn,
    refreshToken = refreshToken,
    refreshTokenExpiresIn = refreshTokenExpiresIn,
)

internal fun randomUsernameAvailableDto(
    username: String = randomUsername(),
    isAvailable: Boolean = randomBoolean(),
): UsernameAvailableDto = UsernameAvailableDto(
    username = username,
    isAvailable = isAvailable,
)

internal fun randomRefreshTokenRequest(refreshToken: String = randomString()): RefreshTokenRequest =
    RefreshTokenRequest(refreshToken)