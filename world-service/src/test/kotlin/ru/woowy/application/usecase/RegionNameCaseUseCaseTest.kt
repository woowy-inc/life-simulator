package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.woowy.application.usecase.impl.RegionNameCaseUseCaseImpl
import ru.woowy.domain.repository.RegionNameCaseRepository
import ru.woowy.helper.randomRegionNameCase

internal class RegionNameCaseUseCaseTest {
    private val caseRepository = mockk<RegionNameCaseRepository>(relaxed = true)
    private val request = randomRegionNameCase()
    private val useCase = RegionNameCaseUseCaseImpl(caseRepository)

    @Test
    fun `name case should be added`() {
        val expected = randomRegionNameCase()
        every { caseRepository.add(request) } returns expected

        val actual = useCase.add(request)
        assertEquals(expected, actual)

        verify(exactly = 1) { caseRepository.add(request) }
    }

    @Test
    fun `name case should be updated`() {
        val expected = randomRegionNameCase()
        every { caseRepository.update(request) } returns expected

        val actual = useCase.update(request)
        assertEquals(expected, actual)

        verify(exactly = 1) { caseRepository.update(request) }
    }

    @Test
    fun `name case should return null when not found on update`() {
        every { caseRepository.update(request) } returns null

        val actual = useCase.update(request)
        assertNull(actual)

        verify(exactly = 1) { caseRepository.update(request) }
    }

    @Test
    fun `name cases should be found by region id`() {
        val expected = listOf(randomRegionNameCase(regionId = request.regionId))
        every { caseRepository.findAllByRegionId(request.regionId) } returns expected

        val actual = useCase.getAll(request.regionId)
        assertEquals(expected, actual)

        verify(exactly = 1) { caseRepository.findAllByRegionId(request.regionId) }
    }

    @Test
    fun `name cases should not be found by region id`() {
        every { caseRepository.findAllByRegionId(any()) } returns emptyList()

        val actual = useCase.getAll(request.regionId)
        assertTrue(actual.isEmpty())

        verify(exactly = 1) { caseRepository.findAllByRegionId(request.regionId) }
    }

    @Test
    fun `name cases should be deleted by region id`() {
        useCase.delete(request.regionId)

        verify(exactly = 1) { caseRepository.deleteAll(request.regionId) }
    }
}