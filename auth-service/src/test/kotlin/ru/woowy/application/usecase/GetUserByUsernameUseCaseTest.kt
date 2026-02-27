package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.woowy.helper.randomUser
import ru.woowy.user.application.usecase.GetUserByUsernameUseCase
import ru.woowy.user.domain.repository.UserRepository
import kotlin.test.assertNull

internal class GetUserByUsernameUseCaseTest {
    private val userRepository = mockk<UserRepository>()
    private val user = randomUser()
    private val useCase = GetUserByUsernameUseCase(userRepository)

    @Test
    fun `should return user by username`() {
        every { userRepository.findByUsername(any()) } returns user

        val expected = useCase(user.username)
        assertEquals(expected, user)

        verify(exactly = 1) { userRepository.findByUsername(user.username) }
    }

    @Test
    fun `should return null if user by username not found`() {
        every { userRepository.findByUsername(any()) } returns null

        val expected = useCase(user.username)
        assertNull(expected)

        verify(exactly = 1) { userRepository.findByUsername(user.username) }
    }
}