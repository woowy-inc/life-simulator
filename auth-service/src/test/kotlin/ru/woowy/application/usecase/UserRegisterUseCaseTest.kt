package ru.woowy.application.usecase

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import ru.woowy.domain.repository.EmailVerificationKeyRepository
import ru.woowy.domain.repository.UserRepository
import ru.woowy.helper.randomEmailVerificationKey
import ru.woowy.helper.randomUser
import ru.woowy.helper.randomUserRegisterRequest
import ru.woowy.infrastructure.mapper.asDto
import kotlin.test.assertEquals

class UserRegisterUseCaseTest {
    private val userRepository = mockk<UserRepository>()
    private val emailVerificationKeyRepository = mockk<EmailVerificationKeyRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val applicationEventPublisher = mockk<ApplicationEventPublisher>()

    private val request = randomUserRegisterRequest()
    private val user = randomUser(request)
    private val expected = user.asDto()

    private val useCase =
        UserRegisterUseCase(
            userRepository,
            emailVerificationKeyRepository,
            passwordEncoder,
            applicationEventPublisher,
        )

    @Test
    fun `user should be added`() {
        every { passwordEncoder.encode(any()) } answers { firstArg() }
        every { userRepository.add(any()) } returns user
        every { emailVerificationKeyRepository.save(any()) } returns randomEmailVerificationKey()
        every { applicationEventPublisher.publishEvent(any<Any>()) } just Runs

        val actual = useCase(request)

        assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.add(any()) }
        verify(exactly = 1) { passwordEncoder.encode(any()) }
        verify(exactly = 1) { emailVerificationKeyRepository.save(any()) }
        verify(exactly = 1) { applicationEventPublisher.publishEvent(any<Any>()) }
    }
}