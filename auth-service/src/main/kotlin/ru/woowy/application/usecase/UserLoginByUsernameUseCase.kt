package ru.woowy.application.usecase

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.application.security.JwtTokenProvider
import ru.woowy.domain.model.TokenDto
import ru.woowy.domain.model.UsernameRequest
import ru.woowy.extension.notFound
import ru.woowy.extension.unauthorized

@Service
@Transactional
internal class UserLoginByUsernameUseCase(
    private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) {
    companion object {
        const val BAD_CREDENTIALS_MESSAGE = "Bad credentials"
    }

    operator fun invoke(request: UsernameRequest): TokenDto {
        val user =
            getUserByUsernameUseCase(request.username)
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