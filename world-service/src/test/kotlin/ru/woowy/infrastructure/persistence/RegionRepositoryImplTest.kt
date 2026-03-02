package ru.woowy.infrastructure.persistence

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.helper.randomRegion
import ru.woowy.infrastructure.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.CrudRegionRepository
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID

internal class RegionRepositoryImplTest
    @Autowired
    constructor(
        private val crudRepository: CrudRegionRepository,
    ) : JpaRepositoryTest() {
        private val repository = RegionRepositoryImpl(crudRepository)

        @BeforeEach
        fun setUp() = crudRepository.deleteAll()

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