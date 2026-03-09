package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import ru.woowy.domain.repository.LocationRepository
import ru.woowy.helper.randomLocation

class LocationUseCaseTest {
    private val locationRepository = mockk<LocationRepository>(relaxed = true)
    private val request = randomLocation()
    private val useCase = LocationUseCaseImpl(locationRepository)

    @Test
    fun `location should be added`() {
        val expected = randomLocation()
        every { locationRepository.add(request) } returns expected

        val actual = useCase.add(request)
        assertEquals(expected, actual)

        verify(exactly = 1) { locationRepository.add(request) }
    }

    @Test
    fun `location should be found by id`() {
        val expected = randomLocation()
        every { locationRepository.findById(request.id) } returns expected

        val actual = useCase.get(request.id)
        assertEquals(expected, actual)

        verify(exactly = 1) { locationRepository.findById(request.id) }
    }

    @Test
    fun `location should return null when not found`() {
        every { locationRepository.findById(any()) } returns null

        val actual = useCase.get(request.id)
        assertNull(actual)

        verify(exactly = 1) { locationRepository.findById(request.id) }
    }

    @Test
    fun `location should be updated`() {
        val expected = randomLocation()
        every { locationRepository.update(request) } returns expected

        val actual = useCase.update(request)
        assertEquals(expected, actual)

        verify(exactly = 1) { locationRepository.update(request) }
    }

    @Test
    fun `location should return null when not found on update`() {
        every { locationRepository.update(request) } returns null

        val actual = useCase.update(request)
        assertNull(actual)

        verify(exactly = 1) { locationRepository.update(request) }
    }

    @Test
    fun `location should be deleted`() {
        useCase.delete(request.id)

        verify(exactly = 1) { locationRepository.delete(request.id) }
    }
}