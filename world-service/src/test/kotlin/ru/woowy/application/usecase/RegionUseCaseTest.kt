package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import ru.woowy.application.usecase.impl.RegionUseCaseImpl
import ru.woowy.domain.repository.RegionRepository
import ru.woowy.helper.randomRegion

internal class RegionUseCaseTest {
    private val regionRepository = mockk<RegionRepository>(relaxed = true)
    private val request = randomRegion()
    private val useCase = RegionUseCaseImpl(regionRepository)

    @Test
    fun `region should be added`() {
        val expected = randomRegion()
        every { regionRepository.add(any()) } returns expected

        val actual = useCase.add(request)
        assertEquals(expected, actual)

        verify(exactly = 1) { regionRepository.add(request) }
    }

    @Test
    fun `region should be updated`() {
        val expected = randomRegion()
        every { regionRepository.update(any()) } returns expected

        val actual = useCase.update(expected)
        assertEquals(expected, actual)

        verify(exactly = 1) { regionRepository.update(expected) }
    }

    @Test
    fun `region should not be updated when region is not found`() {
        val expected = randomRegion()
        every { regionRepository.update(any()) } returns null

        val actual = useCase.update(expected)
        assertNull(actual)

        verify(exactly = 1) { regionRepository.update(expected) }
    }

    @Test
    fun `region should be found by id`() {
        val expected = randomRegion()
        every { regionRepository.findById(any()) } returns expected

        val actual = useCase.get(request.id)
        assertEquals(expected, actual)

        verify(exactly = 1) { regionRepository.findById(request.id) }
    }

    @Test
    fun `region should not be found by id`() {
        every { regionRepository.findById(any()) } returns null

        val actual = useCase.get(request.id)
        assertNull(actual)

        verify(exactly = 1) { regionRepository.findById(request.id) }
    }

    @Test
    fun `region should be deleted`() {
        useCase.delete(request.id)

        verify(exactly = 1) { regionRepository.delete(request.id) }
    }
}