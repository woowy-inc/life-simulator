package ru.woowy.application.usecase

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.context.ApplicationEventPublisher
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.domain.repository.EmailVerificationKeyRepository
import ru.woowy.domain.repository.UserRepository
import ru.woowy.exception.UnauthorizedException
import ru.woowy.helper.randomEmailVerificationKey
import ru.woowy.helper.randomUser
import ru.woowy.infrastructure.mapper.asDto
import ru.woowy.security.UserRole
import java.time.LocalDateTime

internal class VerifyUserEmailUseCaseTest {
    private val emailVerificationKeyRepository = mockk<EmailVerificationKeyRepository>()
    private val userRepository = mockk<UserRepository>()
    private val applicationEventPublisher = mockk<ApplicationEventPublisher>()
    private val user = randomUser()
    private val request =
        randomEmailVerificationKey(
            user = user,
            expiresAt = LocalDateTime.now().plusHours(24),
            used = false,
        )

    private val useCase =
        VerifyUserEmailUseCase(emailVerificationKeyRepository, userRepository, applicationEventPublisher)

    @Test
    fun `should verify user email successfully`() {
        val foundUser = randomUser()
        val updatedUser = foundUser.copy(role = UserRole.USER, isEmailVerified = true)
        val expected = updatedUser.asDto()

        every { emailVerificationKeyRepository.findByKey(any()) } returns request
        every { userRepository.findById(any()) } returns foundUser
        every { emailVerificationKeyRepository.save(any()) } returns randomEmailVerificationKey()
        every { userRepository.update(any()) } returns updatedUser
        every { applicationEventPublisher.publishEvent(any<Any>()) } just Runs

        val actual = useCase(request.key)

        assertEquals(expected, actual)

        verify(exactly = 1) { emailVerificationKeyRepository.findByKey(request.key) }
        verify(exactly = 1) { userRepository.findById(request.user.id) }
        verify(exactly = 1) { emailVerificationKeyRepository.save(request.copy(used = true)) }
        verify(exactly = 1) { userRepository.update(updatedUser) }
        verify(exactly = 1) {
            applicationEventPublisher.publishEvent(
                match<UserRegisteredEvent> { event ->
                    event.userId == updatedUser.id.toString() &&
                        event.username == updatedUser.username &&
                        event.email == updatedUser.email
                },
            )
        }
    }

    @Test
    fun `should throw unauthorized when key not found`() {
        every { emailVerificationKeyRepository.findByKey(any()) } returns null

        val exception =
            assertThrows<UnauthorizedException> {
                useCase(request.key)
            }

        assertEquals(VerifyUserEmailUseCase.ERROR_MESSAGE, exception.message)
    }

    @Test
    fun `should throw unauthorized when key is already used`() {
        every { emailVerificationKeyRepository.findByKey(any()) } returns request.copy(used = true)

        val exception =
            assertThrows<UnauthorizedException> {
                useCase(request.key)
            }

        assertEquals(VerifyUserEmailUseCase.ERROR_MESSAGE, exception.message)
    }

    @Test
    fun `should throw unauthorized when key is expired`() {
        every { emailVerificationKeyRepository.findByKey(any()) } returns
            request.copy(
                expiresAt = LocalDateTime.now().minusHours(1),
            )

        val exception =
            assertThrows<UnauthorizedException> {
                useCase(request.key)
            }

        assertEquals(VerifyUserEmailUseCase.ERROR_MESSAGE, exception.message)
    }

    @Test
    fun `should throw unauthorized when user not found`() {
        every { emailVerificationKeyRepository.findByKey(any()) } returns request
        every { userRepository.findById(any()) } returns null

        val exception =
            assertThrows<UnauthorizedException> {
                useCase(request.key)
            }

        assertEquals(VerifyUserEmailUseCase.ERROR_MESSAGE, exception.message)
    }
}