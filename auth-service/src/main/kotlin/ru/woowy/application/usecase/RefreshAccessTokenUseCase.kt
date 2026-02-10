package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.application.security.JwtTokenProvider
import ru.woowy.domain.model.RefreshTokenRequest
import ru.woowy.domain.model.TokenDto
import ru.woowy.domain.model.TokenType
import ru.woowy.extension.unauthorized

@Service
@Transactional
internal class RefreshAccessTokenUseCase(
    private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    operator fun invoke(request: RefreshTokenRequest): TokenDto {
        val isTokenValid = jwtTokenProvider.isTokenValid(request.refreshToken)
        val isRefreshToken = jwtTokenProvider.extractTokenType(request.refreshToken) == TokenType.REFRESH

        if (!isTokenValid || !isRefreshToken) {
            unauthorized("Refresh token is not valid")
        }

        val username = jwtTokenProvider.extractUsername(request.refreshToken)
        val user = getUserByUsernameUseCase(username) ?: unauthorized("Unknown user")

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