package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.woowy.application.security.JwtTokenProvider
import ru.woowy.domain.model.TokenType
import ru.woowy.exception.UnauthorizedException
import ru.woowy.helper.randomRefreshTokenRequest
import ru.woowy.helper.randomToken
import ru.woowy.helper.randomTokenDto
import ru.woowy.helper.randomUser
import ru.woowy.util.randomUsername
import kotlin.test.assertEquals

class RefreshAccessTokenUseCaseTest {
    private val getUserByUsernameUseCase = mockk<GetUserByUsernameUseCase>()
    private val jwtTokenProvider = mockk<JwtTokenProvider>()
    private val accessToken = randomToken()
    private val refreshToken = randomToken()
    private val request = randomRefreshTokenRequest(refreshToken.value)

    private val useCase = RefreshAccessTokenUseCase(getUserByUsernameUseCase, jwtTokenProvider)

    @Test
    fun `should refresh access token`() {
        val expected = randomTokenDto(accessToken, refreshToken)

        every { jwtTokenProvider.isTokenValid(any()) } returns true
        every { jwtTokenProvider.extractTokenType(any()) } returns TokenType.REFRESH
        every { jwtTokenProvider.extractUsername(any()) } returns randomUsername()
        every { getUserByUsernameUseCase(any()) } returns randomUser()
        every { jwtTokenProvider.generateAccessToken(any()) } returns accessToken
        every { jwtTokenProvider.generateRefreshToken(any()) } returns refreshToken

        val actual = useCase(request)

        assertEquals(expected, actual)

        verify(exactly = 1) { jwtTokenProvider.isTokenValid(any()) }
        verify(exactly = 1) { jwtTokenProvider.extractTokenType(any()) }
        verify(exactly = 1) { jwtTokenProvider.extractUsername(any()) }
        verify(exactly = 1) { getUserByUsernameUseCase(any()) }
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
        every { jwtTokenProvider.extractUsername(any()) } returns randomUsername()
        every { getUserByUsernameUseCase(any()) } returns null

        val actual =
            assertThrows<UnauthorizedException> {
                useCase(request)
            }

        assertEquals(RefreshAccessTokenUseCase.REFRESH_TOKEN_IS_NOT_VALID, actual.message)
    }
}