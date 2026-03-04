package ru.woowy.application.usecase

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.assertThrows
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.domain.repository.UserRepository
import ru.woowy.domain.usecase.EmailVerificationKeyUseCase
import ru.woowy.exception.UnauthorizedException
import ru.woowy.helper.randomEmailVerificationKey
import ru.woowy.helper.randomUser
import ru.woowy.helper.randomUserRegisterRequest
import ru.woowy.helper.randomUsernameAvailableDto
import ru.woowy.infrastructure.mapper.asDto
import ru.woowy.security.UserRole
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertEquals

class UserUseCaseImplTest {
    private val userRepository = mockk<UserRepository>()
    private val emailVerificationKeyUseCase = mockk<EmailVerificationKeyUseCase>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val applicationEventPublisher = mockk<ApplicationEventPublisher>()

    private val useCase =
        UserUseCaseImpl(userRepository, emailVerificationKeyUseCase, passwordEncoder, applicationEventPublisher)

    private val userId = UUID.randomUUID()
    private val user = randomUser()
    private val verificationKeyRequest =
        randomEmailVerificationKey(
            user = user,
            expiresAt = LocalDateTime.now().plusHours(24),
            used = false,
        )

    @Test
    fun `should get user by id`() {
        val expected = randomUser()

        every { userRepository.findById(userId) } returns expected

        val actual = useCase.getByUserId(userId)

        assertNotNull(actual)
        assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.findById(userId) }
    }

    @Test
    fun `should return null when user not found`() {
        every { userRepository.findById(any()) } returns null
        val actual = useCase.getByUserId(userId)

        assertNull(actual)

        verify(exactly = 1) { userRepository.findById(userId) }
    }

    @Test
    fun `should return user by username`() {
        every { userRepository.findByUsername(any()) } returns user

        val expected = useCase.getByUsername(user.username)
        Assertions.assertEquals(expected, user)

        verify(exactly = 1) { userRepository.findByUsername(user.username) }
    }

    @Test
    fun `should return null if user by username not found`() {
        every { userRepository.findByUsername(any()) } returns null

        val expected = useCase.getByUsername(user.username)
        kotlin.test.assertNull(expected)

        verify(exactly = 1) { userRepository.findByUsername(user.username) }
    }

    @Test
    fun `should return true if username is available`() {
        val expected = randomUsernameAvailableDto(user.username, true)

        every { userRepository.isUsernameExists(any()) } returns false

        val actual = useCase.isUsernameAvailable(user.username)
        Assertions.assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.isUsernameExists(user.username) }
    }

    @Test
    fun `should return false if username is not available`() {
        val expected = randomUsernameAvailableDto(user.username, false)
        every { userRepository.isUsernameExists(any()) } returns true

        val actual = useCase.isUsernameAvailable(user.username)
        Assertions.assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.isUsernameExists(user.username) }
    }

    @Test
    fun `user should be added`() {
        val registerRequest = randomUserRegisterRequest()
        val expected = user.asDto()
        val verificationKey = randomEmailVerificationKey(user = user)

        every { passwordEncoder.encode(any()) } answers { firstArg() }
        every { userRepository.add(any()) } returns user
        every { emailVerificationKeyUseCase.add(any()) } returns verificationKey
        every { applicationEventPublisher.publishEvent(any<Any>()) } just Runs

        val actual = useCase.register(registerRequest)

        assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.add(any()) }
        verify(exactly = 1) { passwordEncoder.encode(any()) }
        verify(exactly = 1) { emailVerificationKeyUseCase.add(any()) }
        verify(exactly = 1) { applicationEventPublisher.publishEvent(any<Any>()) }
    }

    @Test
    fun `should verify user email successfully`() {
        val foundUser = randomUser()
        val updatedUser = foundUser.copy(role = UserRole.USER, isEmailVerified = true)
        val expected = updatedUser.asDto()

        every { emailVerificationKeyUseCase.getByKey(any()) } returns verificationKeyRequest
        every { userRepository.findById(any()) } returns foundUser
        every { emailVerificationKeyUseCase.update(any()) } returns randomEmailVerificationKey()
        every { userRepository.update(any()) } returns updatedUser
        every { applicationEventPublisher.publishEvent(any<Any>()) } just Runs

        val actual = useCase.verifyEmail(verificationKeyRequest.key)

        Assertions.assertEquals(expected, actual)

        verify(exactly = 1) { emailVerificationKeyUseCase.getByKey(verificationKeyRequest.key) }
        verify(exactly = 1) { userRepository.findById(verificationKeyRequest.user.id) }
        verify(exactly = 1) { emailVerificationKeyUseCase.update(verificationKeyRequest.copy(used = true)) }
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
        every { emailVerificationKeyUseCase.getByKey(any()) } returns null

        val exception =
            assertThrows<UnauthorizedException> {
                useCase.verifyEmail(verificationKeyRequest.key)
            }

        Assertions.assertEquals(UserUseCaseImpl.INVALID_VERIFICATION_KEY, exception.message)
    }

    @Test
    fun `should throw unauthorized when key is already used`() {
        every { emailVerificationKeyUseCase.getByKey(any()) } returns verificationKeyRequest.copy(used = true)

        val exception =
            assertThrows<UnauthorizedException> {
                useCase.verifyEmail(verificationKeyRequest.key)
            }

        Assertions.assertEquals(UserUseCaseImpl.INVALID_VERIFICATION_KEY, exception.message)
    }

    @Test
    fun `should throw unauthorized when key is expired`() {
        every { emailVerificationKeyUseCase.getByKey(any()) } returns
            verificationKeyRequest.copy(
                expiresAt = LocalDateTime.now().minusHours(1),
            )

        val exception =
            assertThrows<UnauthorizedException> {
                useCase.verifyEmail(verificationKeyRequest.key)
            }

        Assertions.assertEquals(UserUseCaseImpl.INVALID_VERIFICATION_KEY, exception.message)
    }

    @Test
    fun `should throw unauthorized when user not found`() {
        every { emailVerificationKeyUseCase.getByKey(any()) } returns verificationKeyRequest
        every { userRepository.findById(any()) } returns null

        val exception =
            assertThrows<UnauthorizedException> {
                useCase.verifyEmail(verificationKeyRequest.key)
            }

        Assertions.assertEquals(UserUseCaseImpl.INVALID_VERIFICATION_KEY, exception.message)
    }
}