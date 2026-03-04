package ru.woowy.application.usecase

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.woowy.domain.model.RefreshTokenRequest
import ru.woowy.domain.model.TokenDto
import ru.woowy.domain.model.TokenType
import ru.woowy.domain.model.UsernameRequest
import ru.woowy.domain.usecase.AuthUseCase
import ru.woowy.domain.usecase.UserUseCase
import ru.woowy.extension.notFound
import ru.woowy.extension.unauthorized
import ru.woowy.infrastructure.security.JwtTokenProvider

@Service
class AuthUseCaseImpl(
    private val userUseCase: UserUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) : AuthUseCase {
    companion object {
        const val BAD_CREDENTIALS_MESSAGE = "Bad credentials"
        const val REFRESH_TOKEN_IS_NOT_VALID = "Refresh token is not valid"
    }

    override fun refreshAccessToken(request: RefreshTokenRequest): TokenDto {
        val isTokenValid = jwtTokenProvider.isTokenValid(request.refreshToken)
        val isRefreshToken = jwtTokenProvider.extractTokenType(request.refreshToken) == TokenType.REFRESH

        if (!isTokenValid || !isRefreshToken) {
            unauthorized(REFRESH_TOKEN_IS_NOT_VALID)
        }

        val userId = jwtTokenProvider.extractUserId(request.refreshToken)
        val user = userUseCase.getByUserId(userId) ?: unauthorized(REFRESH_TOKEN_IS_NOT_VALID)

        val accessToken = jwtTokenProvider.generateAccessToken(user)
        val refreshToken = jwtTokenProvider.generateRefreshToken(user)

        return TokenDto(
            accessToken = accessToken.value,
            accessTokenExpiresIn = accessToken.expiration,
            refreshToken = refreshToken.value,
            refreshTokenExpiresIn = refreshToken.expiration,
        )
    }

    override fun loginByUsername(request: UsernameRequest): TokenDto {
        val user =
            userUseCase.getByUsername(request.username)
                ?: notFound(BAD_CREDENTIALS_MESSAGE)

        if (!passwordEncoder.matches(request.password, user.password)) {
            unauthorized(BAD_CREDENTIALS_MESSAGE)
        }

        val accessToken = jwtTokenProvider.generateAccessToken(user)
        val refreshToken = jwtTokenProvider.generateRefreshToken(user)

        return TokenDto(
            accessToken = accessToken.value,
            accessTokenExpiresIn = accessToken.expiration,
            refreshToken = refreshToken.value,
            refreshTokenExpiresIn = refreshToken.expiration,
        )
    }
}