package ru.woowy.infrastructure.persistence

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager
import ru.woowy.helper.randomRegion
import ru.woowy.helper.randomRegionNameCase
import ru.woowy.infrastructure.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.JpaRegionNameCaseRepository
import ru.woowy.infrastructure.persistence.jpa.JpaRegionRepository
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID

class RegionNameCaseRepositoryImplTest
    @Autowired
    constructor(
        private val jpaRegionRepository: JpaRegionRepository,
        private val jpaRegionNameCaseRepository: JpaRegionNameCaseRepository,
        private val entityManager: TestEntityManager,
    ) : JpaRepositoryTest() {
        private val regionRepository = RegionRepositoryImpl(jpaRegionRepository)
        private val repository = RegionNameCaseRepositoryImpl(jpaRegionRepository, jpaRegionNameCaseRepository)

        @BeforeEach
        fun setUp() {
            jpaRegionNameCaseRepository.deleteAll()
            jpaRegionRepository.deleteAll()
        }

        @Test
        fun `name case should be added`() {
            val region = regionRepository.add(randomRegion())
            val request = randomRegionNameCase(regionId = region.id)

            val actual = repository.add(request)

            assertEquals(request, actual)
        }

        @Test
        fun `name case should be found by region id`() {
            val region = regionRepository.add(randomRegion())
            val request = randomRegionNameCase(regionId = region.id)
            repository.add(request)

            val actual = repository.findAllByRegionId(region.id)

            assertEquals(listOf(request), actual)
        }

        @Test
        fun `name cases should not be found by region id`() {
            val actual = repository.findAllByRegionId(randomUUID())

            assertTrue(actual.isEmpty())
        }

        @Test
        fun `name case should be updated`() {
            val region = regionRepository.add(randomRegion())
            val request = randomRegionNameCase(regionId = region.id)
            repository.add(request)

            val updated = request.copy(nominative = randomString())
            val actual = repository.update(updated)

            assertEquals(updated, actual)
        }

        @Test
        fun `name case should return null when not found on update`() {
            val actual = repository.update(randomRegionNameCase(regionId = randomUUID()))

            assertNull(actual)
        }

        @Test
        fun `name case should be deleted by region id`() {
            val region = regionRepository.add(randomRegion())
            repository.add(randomRegionNameCase(regionId = region.id))

            repository.deleteAll(region.id)

            assertTrue(repository.findAllByRegionId(region.id).isEmpty())
        }
    }