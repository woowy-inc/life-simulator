package ru.woowy.infrastructure.persistence

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.helper.randomRegion
import ru.woowy.helper.randomRegionNameCase
import ru.woowy.infrastructure.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.CrudRegionNameCaseRepository
import ru.woowy.infrastructure.persistence.jpa.CrudRegionRepository
import ru.woowy.util.randomUUID

internal class RegionNameCaseRepositoryImplTest
    @Autowired
    constructor(
        private val crudRegionRepository: CrudRegionRepository,
        private val crudRepository: CrudRegionNameCaseRepository,
    ) : JpaRepositoryTest() {
        private val regionRepository = RegionRepositoryImpl(crudRegionRepository)
        private val repository = RegionNameCaseRepositoryImpl(crudRepository)

        @BeforeEach
        fun setUp() {
            crudRepository.deleteAll()
            crudRegionRepository.deleteAll()
        }

        @Test
        fun `name case should be added`() {
            val region = regionRepository.add(randomRegion())
            val request = randomRegionNameCase(regionId = region.id)

            val actual = repository.add(request)

            assertEquals(request, actual)
        }

        @Test
        fun `name cases should be added`() {
            val region = regionRepository.add(randomRegion())
            val request = listOf(randomRegionNameCase(regionId = region.id))

            val actual = repository.add(request)

            assertEquals(request, actual)
        }

        @Test
        fun `name cases should be found by region id`() {
            val region = regionRepository.add(randomRegion())
            val request = listOf(randomRegionNameCase(regionId = region.id))
            repository.add(request)

            val actual = repository.findAllByRegionId(region.id)

            assertEquals(request, actual)
        }

        @Test
        fun `name cases should not be found by region id`() {
            val actual = repository.findAllByRegionId(randomUUID())

            assertTrue(actual.isEmpty())
        }

        @Test
        fun `name cases should be deleted by region id`() {
            val region = regionRepository.add(randomRegion())
            repository.add(listOf(randomRegionNameCase(regionId = region.id)))

            repository.deleteAll(region.id)

            assertTrue(repository.findAllByRegionId(region.id).isEmpty())
        }
    }