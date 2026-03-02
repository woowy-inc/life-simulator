package ru.woowy.auth.application.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.auth.domain.model.RefreshTokenRequest
import ru.woowy.auth.domain.model.TokenDto
import ru.woowy.auth.domain.model.TokenType
import ru.woowy.auth.infrastructure.security.JwtTokenProvider
import ru.woowy.extension.unauthorized
import ru.woowy.user.application.usecase.GetUserByIdUseCase
import ru.woowy.user.application.usecase.GetUserByUsernameUseCase

@Service
@Transactional
internal class RefreshAccessTokenUseCase(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    companion object {
        const val REFRESH_TOKEN_IS_NOT_VALID = "Refresh token is not valid"
    }

    operator fun invoke(request: RefreshTokenRequest): TokenDto {
        val isTokenValid = jwtTokenProvider.isTokenValid(request.refreshToken)
        val isRefreshToken = jwtTokenProvider.extractTokenType(request.refreshToken) == TokenType.REFRESH

        if (!isTokenValid || !isRefreshToken) {
            unauthorized(REFRESH_TOKEN_IS_NOT_VALID)
        }

        val userId = jwtTokenProvider.extractUserId(request.refreshToken)
        val user = getUserByIdUseCase(userId) ?: unauthorized(REFRESH_TOKEN_IS_NOT_VALID)

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