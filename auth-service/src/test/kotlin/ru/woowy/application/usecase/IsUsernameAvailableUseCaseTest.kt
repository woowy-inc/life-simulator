package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.woowy.domain.repository.UserRepository
import ru.woowy.helper.randomUsernameAvailableDto
import ru.woowy.util.randomUsername

internal class IsUsernameAvailableUseCaseTest {
    private val userRepository = mockk<UserRepository>()
    private val useCase = IsUsernameAvailableUseCase(userRepository)
    private val username = randomUsername()

    @Test
    fun `should return true if username is available`() {
        val expected = randomUsernameAvailableDto(username, true)

        every { userRepository.isUsernameExists(any()) } returns false

        val actual = useCase(username)
        assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.isUsernameExists(username) }
    }

    @Test
    fun `should return false if username is not available`() {
        val expected = randomUsernameAvailableDto(username, false)
        every { userRepository.isUsernameExists(any()) } returns true

        val actual = useCase(username)
        assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.isUsernameExists(username) }
    }
}