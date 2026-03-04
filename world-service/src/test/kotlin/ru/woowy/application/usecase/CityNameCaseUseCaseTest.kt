package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.woowy.domain.repository.CityNameCaseRepository
import ru.woowy.helper.randomCityNameCase

class CityNameCaseUseCaseTest {
    private val nameCaseRepository = mockk<CityNameCaseRepository>(relaxed = true)
    private val request = randomCityNameCase()
    private val useCase = CityNameCaseUseCaseImpl(nameCaseRepository)

    @Test
    fun `name case should be added`() {
        val expected = randomCityNameCase()
        every { nameCaseRepository.add(request) } returns expected

        val actual = useCase.add(request)
        assertEquals(expected, actual)

        verify(exactly = 1) { nameCaseRepository.add(request) }
    }

    @Test
    fun `name cases should be found by city id`() {
        val expected = listOf(randomCityNameCase(cityId = request.cityId))
        every { nameCaseRepository.findAllByCityId(request.cityId) } returns expected

        val actual = useCase.findAll(request.cityId)
        assertEquals(expected, actual)

        verify(exactly = 1) { nameCaseRepository.findAllByCityId(request.cityId) }
    }

    @Test
    fun `name cases should not be found by city id`() {
        every { nameCaseRepository.findAllByCityId(any()) } returns emptyList()

        val actual = useCase.findAll(request.cityId)
        assertTrue(actual.isEmpty())

        verify(exactly = 1) { nameCaseRepository.findAllByCityId(request.cityId) }
    }

    @Test
    fun `name cases should be deleted by city id`() {
        useCase.deleteAll(request.cityId)

        verify(exactly = 1) { nameCaseRepository.deleteAll(request.cityId) }
    }
}