package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.woowy.auth.application.usecase.RefreshAccessTokenUseCase
import ru.woowy.auth.domain.model.TokenType
import ru.woowy.auth.infrastructure.security.JwtTokenProvider
import ru.woowy.exception.UnauthorizedException
import ru.woowy.helper.randomRefreshTokenRequest
import ru.woowy.helper.randomToken
import ru.woowy.helper.randomTokenDto
import ru.woowy.helper.randomUser
import ru.woowy.user.application.usecase.GetUserByIdUseCase
import ru.woowy.user.application.usecase.GetUserByUsernameUseCase
import ru.woowy.util.randomUUID
import ru.woowy.util.randomUsername
import kotlin.test.assertEquals

internal class RefreshAccessTokenUseCaseTest {
    private val getUserByIdUseCase = mockk<GetUserByIdUseCase>()
    private val jwtTokenProvider = mockk<JwtTokenProvider>()
    private val accessToken = randomToken()
    private val refreshToken = randomToken()
    private val request = randomRefreshTokenRequest(refreshToken.value)

    private val useCase = RefreshAccessTokenUseCase(getUserByIdUseCase, jwtTokenProvider)

    @Test
    fun `should refresh access token`() {
        val expected = randomTokenDto(accessToken, refreshToken)

        every { jwtTokenProvider.isTokenValid(any()) } returns true
        every { jwtTokenProvider.extractTokenType(any()) } returns TokenType.REFRESH
        every { jwtTokenProvider.extractUserId(any()) } returns randomUUID()
        every { getUserByIdUseCase(any()) } returns randomUser()
        every { jwtTokenProvider.generateAccessToken(any()) } returns accessToken
        every { jwtTokenProvider.generateRefreshToken(any()) } returns refreshToken

        val actual = useCase(request)

        assertEquals(expected, actual)

        verify(exactly = 1) { jwtTokenProvider.isTokenValid(any()) }
        verify(exactly = 1) { jwtTokenProvider.extractTokenType(any()) }
        verify(exactly = 1) { jwtTokenProvider.extractUserId(any()) }
        verify(exactly = 1) { getUserByIdUseCase(any()) }
        verify(exactly = 1) { jwtTokenProvider.generateAccessToken(any()) }
        verify(exactly = 1) { jwtTokenProvider.generateRefreshToken(any()) }
    }

    @Test
    fun `should throw exception when refresh token is not valid`() {
        every { jwtTokenProvider.isTokenValid(any()) } returns false
        every { jwtTokenProvider.extractTokenType(any()) } returns TokenType.ACCESS

        val actual =
            assertThrows<UnauthorizedException> {
                useCase(request)
            }

        assertEquals(RefreshAccessTokenUseCase.REFRESH_TOKEN_IS_NOT_VALID, actual.message)
    }

    @Test
    fun `should throw exception when get user by username returns null`() {
        every { jwtTokenProvider.isTokenValid(any()) } returns true
        every { jwtTokenProvider.extractTokenType(any()) } returns TokenType.REFRESH
        every { jwtTokenProvider.extractUserId(any()) } returns randomUUID()
        every { getUserByIdUseCase(any()) } returns null

        val actual =
            assertThrows<UnauthorizedException> {
                useCase(request)
            }

        assertEquals(RefreshAccessTokenUseCase.REFRESH_TOKEN_IS_NOT_VALID, actual.message)
    }
}