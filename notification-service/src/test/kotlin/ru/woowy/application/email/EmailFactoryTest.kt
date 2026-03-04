package ru.woowy.application.email

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import ru.woowy.domain.model.UserRegisterRequestedEvent
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.domain.model.VerifiedEmail
import ru.woowy.domain.model.VerifyEmail

class EmailFactoryTest {
    private val factory = EmailFactory()
    private val body = "<html>body</html>"

    @Test
    fun `should return VerifyEmail for UserRegisterRequestedEvent`() {
        val event = mockk<UserRegisterRequestedEvent>()
        every { event.email } returns "test@test.com"

        val result = factory.getEmail(event, body)

        assertInstanceOf(VerifyEmail::class.java, result)
        assertEquals("test@test.com", result.to)
    }

    @Test
    fun `should return VerifiedEmail for UserRegisteredEvent`() {
        val event = mockk<UserRegisteredEvent>()
        every { event.email } returns "test@test.com"

        val result = factory.getEmail(event, body)

        assertInstanceOf(VerifiedEmail::class.java, result)
        assertEquals("test@test.com", result.to)
    }
}