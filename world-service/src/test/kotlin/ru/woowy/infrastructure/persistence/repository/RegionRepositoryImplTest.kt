package ru.woowy.infrastructure.persistence.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager
import ru.woowy.helper.randomRegion
import ru.woowy.infrastructure.persistence.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.RegionJpaRepository
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID

class RegionRepositoryImplTest
    @Autowired
    constructor(
        private val regionJpaRepository: RegionJpaRepository,
        private val entityManager: TestEntityManager,
    ) : JpaRepositoryTest() {
        private val repository = RegionRepositoryImpl(regionJpaRepository)

        @BeforeEach
        fun setUp() = regionJpaRepository.deleteAll()

        @Test
        fun `region should be added`() {
            val request = randomRegion()
            val actual = repository.add(request)
            assertEquals(request, actual)
        }

        @Test
        fun `region should be found by id`() {
            val request = randomRegion()
            repository.add(request)

            entityManager.flush()
            entityManager.clear()

            val actual = repository.findById(request.id)

            assertEquals(request, actual)
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