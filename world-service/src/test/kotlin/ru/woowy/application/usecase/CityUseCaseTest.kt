package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import ru.woowy.application.usecase.impl.CityUseCaseImpl
import ru.woowy.domain.repository.CityRepository
import ru.woowy.helper.randomCity

internal class CityUseCaseTest {
    private val cityRepository = mockk<CityRepository>(relaxed = true)
    private val request = randomCity()
    private val useCase = CityUseCaseImpl(cityRepository)

    @Test
    fun `city should be added`() {
        val expected = randomCity()
        every { cityRepository.add(request) } returns expected

        val actual = useCase.add(request)
        assertEquals(expected, actual)

        verify(exactly = 1) { cityRepository.add(request) }
    }

    @Test
    fun `city should be found by id`() {
        val expected = randomCity()
        every { cityRepository.findById(request.id) } returns expected

        val actual = useCase.get(request.id)
        assertEquals(expected, actual)

        verify(exactly = 1) { cityRepository.findById(request.id) }
    }

    @Test
    fun `city should return null when not found`() {
        every { cityRepository.findById(any()) } returns null

        val actual = useCase.get(request.id)
        assertNull(actual)

        verify(exactly = 1) { cityRepository.findById(request.id) }
    }

    @Test
    fun `city should be updated`() {
        val expected = randomCity()
        every { cityRepository.update(request) } returns expected

        val actual = useCase.update(request)
        assertEquals(expected, actual)

        verify(exactly = 1) { cityRepository.update(request) }
    }

    @Test
    fun `city should return null when not found on update`() {
        every { cityRepository.update(request) } returns null

        val actual = useCase.update(request)
        assertNull(actual)

        verify(exactly = 1) { cityRepository.update(request) }
    }

    @Test
    fun `city should be deleted`() {
        useCase.delete(request.id)

        verify(exactly = 1) { cityRepository.delete(request.id) }
    }
}