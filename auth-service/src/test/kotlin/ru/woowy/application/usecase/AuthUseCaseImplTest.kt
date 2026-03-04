package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder
import ru.woowy.domain.model.TokenType
import ru.woowy.domain.usecase.UserUseCase
import ru.woowy.exception.NotFoundException
import ru.woowy.exception.UnauthorizedException
import ru.woowy.helper.randomRefreshTokenRequest
import ru.woowy.helper.randomToken
import ru.woowy.helper.randomTokenDto
import ru.woowy.helper.randomUser
import ru.woowy.helper.randomUsernameRequest
import ru.woowy.infrastructure.security.JwtTokenProvider
import ru.woowy.util.randomUUID
import kotlin.test.assertEquals

class AuthUseCaseImplTest {
    private val userUseCase = mockk<UserUseCase>()
    private val jwtTokenProvider = mockk<JwtTokenProvider>()
    private val passwordEncoder = mockk<PasswordEncoder>()

    private val useCase = AuthUseCaseImpl(userUseCase, jwtTokenProvider, passwordEncoder)

    @Test
    fun `should refresh access token`() {
        val accessToken = randomToken()
        val refreshToken = randomToken()
        val request = randomRefreshTokenRequest(refreshToken.value)
        val expected = randomTokenDto(accessToken, refreshToken)

        every { jwtTokenProvider.isTokenValid(any()) } returns true
        every { jwtTokenProvider.extractTokenType(any()) } returns TokenType.REFRESH
        every { jwtTokenProvider.extractUserId(any()) } returns randomUUID()
        every { userUseCase.getByUserId(any()) } returns randomUser()
        every { jwtTokenProvider.generateAccessToken(any()) } returns accessToken
        every { jwtTokenProvider.generateRefreshToken(any()) } returns refreshToken

        val actual = useCase.refreshAccessToken(request)

        assertEquals(expected, actual)

        verify(exactly = 1) { jwtTokenProvider.isTokenValid(any()) }
        verify(exactly = 1) { jwtTokenProvider.extractTokenType(any()) }
        verify(exactly = 1) { jwtTokenProvider.extractUserId(any()) }
        verify(exactly = 1) { userUseCase.getByUserId(any()) }
        verify(exactly = 1) { jwtTokenProvider.generateAccessToken(any()) }
        verify(exactly = 1) { jwtTokenProvider.generateRefreshToken(any()) }
    }

    @Test
    fun `should throw exception when refresh token is not valid`() {
        val request = randomRefreshTokenRequest()

        every { jwtTokenProvider.isTokenValid(any()) } returns false
        every { jwtTokenProvider.extractTokenType(any()) } returns TokenType.ACCESS

        val actual =
            assertThrows<UnauthorizedException> {
                useCase.refreshAccessToken(request)
            }

        assertEquals(AuthUseCaseImpl.REFRESH_TOKEN_IS_NOT_VALID, actual.message)
    }

    @Test
    fun `should throw exception when user by id not found on refresh`() {
        val request = randomRefreshTokenRequest()

        every { jwtTokenProvider.isTokenValid(any()) } returns true
        every { jwtTokenProvider.extractTokenType(any()) } returns TokenType.REFRESH
        every { jwtTokenProvider.extractUserId(any()) } returns randomUUID()
        every { userUseCase.getByUserId(any()) } returns null

        val actual =
            assertThrows<UnauthorizedException> {
                useCase.refreshAccessToken(request)
            }

        assertEquals(AuthUseCaseImpl.REFRESH_TOKEN_IS_NOT_VALID, actual.message)
    }

    @Test
    fun `should login by username and return tokens`() {
        val accessToken = randomToken()
        val refreshToken = randomToken()
        val request = randomUsernameRequest()
        val expected = randomTokenDto(accessToken, refreshToken)

        every { userUseCase.getByUsername(any()) } returns randomUser()
        every { passwordEncoder.matches(any(), any()) } returns true
        every { jwtTokenProvider.generateAccessToken(any()) } returns accessToken
        every { jwtTokenProvider.generateRefreshToken(any()) } returns refreshToken

        val actual = useCase.loginByUsername(request)

        assertEquals(expected, actual)

        verify(exactly = 1) { userUseCase.getByUsername(request.username) }
        verify(exactly = 1) { jwtTokenProvider.generateAccessToken(any()) }
        verify(exactly = 1) { jwtTokenProvider.generateRefreshToken(any()) }
    }

    @Test
    fun `should throw not found when user by username not found`() {
        val request = randomUsernameRequest()

        every { userUseCase.getByUsername(any()) } returns null

        val actual =
            assertThrows<NotFoundException> {
                useCase.loginByUsername(request)
            }

        assertEquals(AuthUseCaseImpl.BAD_CREDENTIALS_MESSAGE, actual.message)
    }

    @Test
    fun `should throw unauthorized when password does not match`() {
        val request = randomUsernameRequest()

        every { userUseCase.getByUsername(any()) } returns randomUser()
        every { passwordEncoder.matches(any(), any()) } returns false

        val actual =
            assertThrows<UnauthorizedException> {
                useCase.loginByUsername(request)
            }

        assertEquals(AuthUseCaseImpl.BAD_CREDENTIALS_MESSAGE, actual.message)
    }
}