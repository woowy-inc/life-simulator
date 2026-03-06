package ru.woowy.infrastructure.persistence.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager
import ru.woowy.helper.randomRegion
import ru.woowy.helper.randomRegionNameCase
import ru.woowy.infrastructure.persistence.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.JpaRegionRepository
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID

class RegionRepositoryImplTest
    @Autowired
    constructor(
        private val jpaRegionRepository: JpaRegionRepository,
        private val jpaRegionNameCaseRepository: JpaRegionNameCaseRepository,
        private val entityManager: TestEntityManager,
    ) : JpaRepositoryTest() {
        private val nameCaseRepository = RegionNameCaseRepositoryImpl(jpaRegionRepository, jpaRegionNameCaseRepository)
        private val repository = RegionRepositoryImpl(jpaRegionRepository)

        @BeforeEach
        fun setUp() = jpaRegionRepository.deleteAll()

        @Test
        fun `region should be added`() {
            val request = randomRegion(nameCase = null)
            val actual = repository.add(request)
            assertEquals(request, actual)
        }

        @Test
        fun `region should be found by id`() {
            val request = randomRegion(nameCase = null)
            repository.add(request)
            val nameCase = randomRegionNameCase(regionId = request.id)
            nameCaseRepository.add(nameCase)

            entityManager.flush()
            entityManager.clear()

            val actual = repository.findById(request.id)

            assertEquals(request.copy(nameCase = nameCase), actual)
        }

        @Test
        fun `region should not be found by id`() {
            val actual = repository.findById(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `region should be updated`() {
            val saved = repository.add(randomRegion())
            val request = saved.copy(name = randomString())

            val actual = repository.update(request)

            assertEquals(request, actual)
        }

        @Test
        fun `region should be deleted`() {
            val saved = repository.add(randomRegion())

            repository.delete(saved.id)

            assertNull(repository.findById(saved.id))
        }
    }