package ru.woowy.application.usecase

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.application.security.JwtTokenProvider
import ru.woowy.domain.model.TokenDto
import ru.woowy.domain.model.UsernameRequest
import ru.woowy.domain.repository.UserRepository
import ru.woowy.extension.notFound
import ru.woowy.extension.unauthorized

@Service
@Transactional
internal class UserLoginByUsernameUseCase(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) {
    operator fun invoke(request: UsernameRequest): TokenDto {
        val user =
            userRepository
                .findByUsername(request.username)
                ?: notFound("User[username:${request.username}] not found")

        if (!passwordEncoder.matches(request.password, user.password)) {
            unauthorized("Bad credentials")
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