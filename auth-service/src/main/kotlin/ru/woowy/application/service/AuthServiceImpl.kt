package ru.woowy.application.service

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.woowy.application.config.AppProperties
import ru.woowy.application.security.JwtTokenProvider
import ru.woowy.application.usecase.GetUserByUsernameUseCase
import ru.woowy.domain.model.TokenResponse
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.model.UsernameRequest
import ru.woowy.domain.repository.UserRepository
import ru.woowy.domain.service.AuthService
import ru.woowy.extension.internalError
import ru.woowy.extension.notFound
import ru.woowy.extension.unauthorized
import ru.woowy.infrastructure.mapping.asDto
import ru.woowy.security.UserDto
import java.security.interfaces.RSAPublicKey

@Service
@Transactional
internal class AuthServiceImpl(
    private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val appProperties: AppProperties,
    private val rsaPublicKey: RSAPublicKey,
    private val passwordEncoder: PasswordEncoder,
) : AuthService {
    override fun loginByUsername(request: UsernameRequest): TokenResponse {
        val user =
            getUserByUsernameUseCase(request.username)
                ?: notFound("User[username:${request.username}] not found")

        if (!passwordEncoder.matches(request.password, user.password)) {
            unauthorized("Bad credentials")
        }

        val token = jwtTokenProvider.generateToken(user)

        return TokenResponse(token, appProperties.jwt.expiration)
    }

    override fun registerUser(request: UserRegisterRequest): UserDto {
        val hashedPassword =
            passwordEncoder
                .encode(request.password)
                ?: internalError("Encode password error")

        return userRepository.addUser(request.copy(password = hashedPassword)).asDto()
    }

    override fun getJwks(): Map<String, Any> {
        val jwk =
            RSAKey
                .Builder(rsaPublicKey)
                .keyID(appProperties.jwt.keyId)
                .algorithm(JWSAlgorithm.RS256)
                .keyUse(KeyUse.SIGNATURE)
                .build()

        return mapOf("keys" to listOf(jwk.toJSONObject()))
    }
}