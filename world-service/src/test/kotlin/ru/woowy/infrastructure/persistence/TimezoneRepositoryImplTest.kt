package ru.woowy.infrastructure.persistence

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.helper.randomTimezone
import ru.woowy.infrastructure.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.JpaTimezoneRepository
import ru.woowy.util.randomString

internal class TimezoneRepositoryImplTest
    @Autowired
    constructor(
        private val jpaTimezoneRepository: JpaTimezoneRepository,
    ) : JpaRepositoryTest() {
        private val repository = TimezoneRepositoryImpl(jpaTimezoneRepository)

        @BeforeEach
        fun setUp() = jpaTimezoneRepository.deleteAll()

        @Test
        fun `timezone should be added`() {
            val request = randomTimezone()

            val actual = repository.add(request)

            assertEquals(request, actual)
        }

        @Test
        fun `timezone should be found by timezone id`() {
            val request = randomTimezone()
            repository.add(request)

            val actual = repository.findByTimezoneId(request.timezoneId)

            assertEquals(request, actual)
        }

        @Test
        fun `timezone should not be found by timezone id`() {
            val actual = repository.findByTimezoneId(randomString())

            assertNull(actual)
        }

        @Test
        fun `timezone should be updated`() {
            val saved = repository.add(randomTimezone())
            val request = saved.copy(abbreviation = randomString())

            val actual = repository.update(request)

            assertEquals(request, actual)
        }

        @Test
        fun `timezone should be deleted`() {
            val saved = repository.add(randomTimezone())

            repository.delete(saved.timezoneId)

            assertNull(repository.findByTimezoneId(saved.timezoneId))
        }
    }