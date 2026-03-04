package ru.woowy.application.usecase

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import ru.woowy.application.email.EmailBodyResolver
import ru.woowy.application.email.EmailFactory
import ru.woowy.application.email.EmailTemplateRenderer
import ru.woowy.domain.model.Email
import ru.woowy.domain.model.EmailBody
import ru.woowy.domain.model.Event
import ru.woowy.domain.service.EmailService

class EmailUseCaseImplTest {
    private val emailService = mockk<EmailService>()
    private val emailFactory = mockk<EmailFactory>()
    private val emailBodyResolver = mockk<EmailBodyResolver>()
    private val renderer = mockk<EmailTemplateRenderer>()
    private val useCase = EmailUseCaseImpl(emailService, emailFactory, emailBodyResolver, renderer)

    private val event = mockk<Event>()
    private val emailBody = mockk<EmailBody>()
    private val email = mockk<Email>()
    private val renderedBody = "<html>body</html>"

    @Test
    fun `should send email successfully`() = runTest {
        every { emailBodyResolver.resolve(event) } returns emailBody
        every { renderer.render(emailBody) } returns renderedBody
        every { emailFactory.getEmail(event, renderedBody) } returns email
        every { email.to } returns "test@test.com"
        coEvery { emailService.send(email) } just Runs

        useCase.send(event)

        coVerify(exactly = 1) { emailService.send(email) }
    }

    @Test
    fun `should not throw when email service fails`() = runTest {
        every { emailBodyResolver.resolve(event) } returns emailBody
        every { renderer.render(emailBody) } returns renderedBody
        every { emailFactory.getEmail(event, renderedBody) } returns email
        every { email.to } returns "test@test.com"
        coEvery { emailService.send(email) } throws RuntimeException()

        assertDoesNotThrow { useCase.send(event) }
    }

    @Test
    fun `should not throw when resolver fails`() = runTest {
        every { emailBodyResolver.resolve(event) } throws RuntimeException()

        assertDoesNotThrow { useCase.send(event) }

        coVerify(exactly = 0) { emailService.send(any()) }
    }
}