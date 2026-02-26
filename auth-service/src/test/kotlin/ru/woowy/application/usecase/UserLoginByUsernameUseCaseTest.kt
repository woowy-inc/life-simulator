package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder
import ru.woowy.application.security.JwtTokenProvider
import ru.woowy.exception.NotFoundException
import ru.woowy.exception.UnauthorizedException
import ru.woowy.helper.randomToken
import ru.woowy.helper.randomTokenDto
import ru.woowy.helper.randomUser
import ru.woowy.helper.randomUsernameRequest
import kotlin.test.assertEquals

internal class UserLoginByUsernameUseCaseTest {
    private val getUserByUsernameUseCase = mockk<GetUserByUsernameUseCase>()
    private val jwtTokenProvider = mockk<JwtTokenProvider>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val request = randomUsernameRequest()

    private val accessToken = randomToken()
    private val refreshToken = randomToken()
    private val expected = randomTokenDto(accessToken, refreshToken)

    private val useCase = UserLoginByUsernameUseCase(getUserByUsernameUseCase, jwtTokenProvider, passwordEncoder)

    @Test
    fun `should login by username and return tokens`() {
        every { getUserByUsernameUseCase(any()) } returns randomUser()
        every { passwordEncoder.matches(any(), any()) } returns true
        every { jwtTokenProvider.generateAccessToken(any()) } returns accessToken
        every { jwtTokenProvider.generateRefreshToken(any()) } returns refreshToken

        val actual = useCase(request)

        assertEquals(expected, actual)

        verify(exactly = 1) { getUserByUsernameUseCase(request.username) }
        verify(exactly = 1) { jwtTokenProvider.generateAccessToken(any()) }
        verify(exactly = 1) { jwtTokenProvider.generateRefreshToken(any()) }
    }

    @Test
    fun `should be not found exception on get user by username`() {
        every { getUserByUsernameUseCase(any()) } returns null

        val expected =
            assertThrows<NotFoundException> {
                useCase(request)
            }

        assertEquals(expected.message, UserLoginByUsernameUseCase.BAD_CREDENTIALS_MESSAGE)
    }

    @Test
    fun `password encoder should be false on password not match`() {
        every { getUserByUsernameUseCase(any()) } returns randomUser()
        every { passwordEncoder.matches(any(), any()) } returns false

        val expected =
            assertThrows<UnauthorizedException> {
                useCase(request)
            }

        assertEquals(expected.message, UserLoginByUsernameUseCase.BAD_CREDENTIALS_MESSAGE)
    }
}