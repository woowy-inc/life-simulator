package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.woowy.application.usecase.impl.RegionNameCaseUseCaseImpl
import ru.woowy.domain.repository.RegionNameCaseRepository
import ru.woowy.helper.randomRegionNameCase
import ru.woowy.util.randomUUID

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
    fun `name cases should be added`() {
        val requests = listOf(randomRegionNameCase(), randomRegionNameCase())
        val expected = listOf(randomRegionNameCase(), randomRegionNameCase())
        every { caseRepository.add(requests) } returns expected

        val actual = useCase.add(requests)
        assertEquals(expected, actual)

        verify(exactly = 1) { caseRepository.add(requests) }
    }

    @Test
    fun `name cases should be found by region id`() {
        val expected = listOf(randomRegionNameCase(regionId = request.regionId))
        every { caseRepository.findAllByRegionId(any()) } returns expected

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
    fun `name cases should be updated`() {
        val regionId1 = randomUUID()
        val regionId2 = randomUUID()
        val requests =
            listOf(
                randomRegionNameCase(regionId = regionId1),
                randomRegionNameCase(regionId = regionId1),
                randomRegionNameCase(regionId = regionId2),
            )
        val expected = listOf(randomRegionNameCase(), randomRegionNameCase())
        every { caseRepository.add(requests) } returns expected

        val actual = useCase.update(requests)
        assertEquals(expected, actual)

        verify(exactly = 1) { caseRepository.deleteAll(regionId1) }
        verify(exactly = 1) { caseRepository.deleteAll(regionId2) }
        verify(exactly = 1) { caseRepository.add(requests) }
    }

    @Test
    fun `deleteAll should be called once per unique region id on update`() {
        val regionId = randomUUID()
        val requests =
            listOf(
                randomRegionNameCase(regionId = regionId),
                randomRegionNameCase(regionId = regionId),
            )

        every { caseRepository.add(requests) } returns requests

        useCase.update(requests)

        verify(exactly = 1) { caseRepository.deleteAll(regionId) }
    }

    @Test
    fun `name cases should be deleted by region id`() {
        useCase.delete(request.regionId)

        verify(exactly = 1) { caseRepository.deleteAll(request.regionId) }
    }
}