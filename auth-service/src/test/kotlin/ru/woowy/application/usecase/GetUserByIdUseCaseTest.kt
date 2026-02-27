package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import ru.woowy.helper.randomUser
import ru.woowy.user.application.usecase.GetUserByIdUseCase
import ru.woowy.user.domain.repository.UserRepository
import ru.woowy.user.infrastructure.mapper.asDto
import java.util.UUID
import kotlin.test.assertEquals

internal class GetUserByIdUseCaseTest {
    private val userRepository = mockk<UserRepository>()
    private val useCase = GetUserByIdUseCase(userRepository)
    private val userId = UUID.randomUUID()

    @Test
    fun `should get user by id`() {
        val user = randomUser()
        val expected = user.asDto()

        every { userRepository.findById(userId) } returns user

        val actual = useCase(userId)

        assertNotNull(actual)
        assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.findById(userId) }
    }

    @Test
    fun `should return null when user not found`() {
        every { userRepository.findById(any()) } returns null
        val actual = useCase(userId)

        assertNull(actual)

        verify(exactly = 1) { userRepository.findById(userId) }
    }
}