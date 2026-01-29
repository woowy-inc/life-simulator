package ru.woowy.application.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.woowy.application.config.JwtProperties
import ru.woowy.application.security.JwtTokenProvider
import ru.woowy.application.usecase.GetUserByUsernameUseCase
import ru.woowy.domain.model.TokenResponse
import ru.woowy.domain.model.UsernameRequest
import ru.woowy.domain.service.AuthService
import ru.woowy.extension.notFound
import ru.woowy.extension.unauthorized

@Service
internal class AuthServiceImpl(
    private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtProperties: JwtProperties,
    private val passwordEncoder: PasswordEncoder,
) : AuthService {
    override fun authByUsername(request: UsernameRequest): TokenResponse {
        val user =
            getUserByUsernameUseCase(request.username)
                ?: notFound("User[username:${request.username}] not found")

        if (passwordEncoder.matches(request.password, user.password)) {
            unauthorized("Bad credentials")
        }

        val token = jwtTokenProvider.generateToken(user)

        return TokenResponse(token, jwtProperties.expiration)
    }
}