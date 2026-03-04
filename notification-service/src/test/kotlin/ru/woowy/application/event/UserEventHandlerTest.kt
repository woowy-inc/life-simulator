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
import ru.woowy.domain.model.Event
import ru.woowy.domain.usecase.EmailUseCase

class UserEventHandlerTest {
    private val useCase = mockk<EmailUseCase>()
    private val handler = UserEventHandler(useCase)

    private val event = mockk<Event>()
    private val ack = mockk<Acknowledgment>()

    @Test
    fun `should handle user event and acknowledge`() = runTest {
        coEvery { useCase.send(event) } just Runs
        every { ack.acknowledge() } just Runs

        handler.handleUserEvent(event, ack)

        coVerify(exactly = 1) { useCase.send(event) }
        verify(exactly = 1) { ack.acknowledge() }
    }
}