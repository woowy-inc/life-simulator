package ru.woowy.application.event

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.kafka.support.Acknowledgment
import ru.woowy.application.usecase.SendEmailUseCase
import ru.woowy.domain.model.Event

class UserEventHandlerTest {
    private val sendEmailUseCase = mockk<SendEmailUseCase>()
    private val handler = UserEventHandler(sendEmailUseCase)

    private val event = mockk<Event>()
    private val ack = mockk<Acknowledgment>()

    @Test
    fun `should handle user event and acknowledge`() = runTest {
        coEvery { sendEmailUseCase(event) } just Runs
        every { ack.acknowledge() } just Runs

        handler.handleUserEvent(event, ack)

        coVerify(exactly = 1) { sendEmailUseCase(event) }
        verify(exactly = 1) { ack.acknowledge() }
    }
}