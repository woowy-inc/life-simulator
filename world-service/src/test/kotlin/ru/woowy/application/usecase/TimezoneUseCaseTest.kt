package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import ru.woowy.domain.repository.TimezoneRepository
import ru.woowy.helper.randomTimezone
import ru.woowy.util.randomString
import kotlin.test.assertEquals

class TimezoneUseCaseTest {
    private val timezoneRepository = mockk<TimezoneRepository>(relaxed = true)
    private val request = randomTimezone()
    private val useCase = TimezoneUseCaseImpl(timezoneRepository)

    @Test
    fun `timezone should be added`() {
        val expected = randomTimezone()
        every { timezoneRepository.add(any()) } returns expected

        val actual = useCase.add(request)
        assertEquals(expected, actual)

        verify(exactly = 1) { timezoneRepository.add(request) }
    }

    @Test
    fun `timezone should be updated`() {
        val expected = randomTimezone()
        every { timezoneRepository.update(any()) } returns expected

        val actual = useCase.update(expected)
        assertEquals(expected, actual)

        verify(exactly = 1) { timezoneRepository.update(expected) }
    }

    @Test
    fun `timezone should not be updated when timezone is not found`() {
        val expected = randomTimezone()
        every { timezoneRepository.update(any()) } returns null

        val actual = useCase.update(expected)
        assertNull(actual)

        verify(exactly = 1) { timezoneRepository.update(expected) }
    }

    @Test
    fun `timezone should be found by timezone id`() {
        val timezoneId = randomString()
        val expected = randomTimezone()
        every { timezoneRepository.findByTimezoneId(any()) } returns expected

        val actual = useCase.get(timezoneId)
        assertEquals(expected, actual)

        verify(exactly = 1) { timezoneRepository.findByTimezoneId(timezoneId) }
    }

    @Test
    fun `timezone should not be found by timezone id`() {
        val timezoneId = randomString()
        every { timezoneRepository.findByTimezoneId(any()) } returns null

        val actual = useCase.get(timezoneId)
        assertNull(actual)

        verify(exactly = 1) { timezoneRepository.findByTimezoneId(timezoneId) }
    }

    @Test
    fun `timezone should be deleted`() {
        useCase.delete(request.timezoneId)

        verify(exactly = 1) { timezoneRepository.delete(request.timezoneId) }
    }
}