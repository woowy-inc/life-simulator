package ru.woowy.infrastructure.persistence.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager
import ru.woowy.domain.model.LocationPageable
import ru.woowy.helper.randomLocation
import ru.woowy.helper.randomRegion
import ru.woowy.helper.randomTimezone
import ru.woowy.infrastructure.persistence.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.LocationJpaRepository
import ru.woowy.infrastructure.persistence.jpa.RegionJpaRepository
import ru.woowy.infrastructure.persistence.jpa.TimezoneJpaRepository
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID

class LocationRepositoryImplTest
    @Autowired
    constructor(
        private val locationJpaRepository: LocationJpaRepository,
        private val regionJpaRepository: RegionJpaRepository,
        private val timezoneJpaRepository: TimezoneJpaRepository,
        private val testEntityManager: TestEntityManager,
    ) : JpaRepositoryTest() {
        private val regionRepository = RegionRepositoryImpl(regionJpaRepository)
        private val timezoneRepository = TimezoneRepositoryImpl(timezoneJpaRepository)
        private val repository =
            LocationRepositoryImpl(locationJpaRepository, regionJpaRepository, timezoneJpaRepository)

        @BeforeEach
        fun setUp() {
            locationJpaRepository.deleteAll()
            regionJpaRepository.deleteAll()
            timezoneJpaRepository.deleteAll()
        }

        @Test
        fun `location should be added`() {
            val region = regionRepository.add(randomRegion())
            val timezone = timezoneRepository.add(randomTimezone())
            val request = randomLocation(region = region, timezone = timezone)

            val actual = repository.add(request)

            assertEquals(request, actual)
        }

        @Test
        fun `locations should be found`() {
            val region = regionRepository.add(randomRegion())
            val timezone = timezoneRepository.add(randomTimezone())
            val request = randomLocation(region = region, timezone = timezone)
            val expected = LocationPageable(0, 1, 1, listOf(request))
            repository.add(request)

            testEntityManager.flush()
            testEntityManager.clear()

            val actual = repository.findAll()

            assertEquals(expected, actual)
        }

        @Test
        fun `location should be found by id`() {
            val region = regionRepository.add(randomRegion())
            val timezone = timezoneRepository.add(randomTimezone())
            val request = randomLocation(region = region, timezone = timezone)
            repository.add(request)

            testEntityManager.flush()
            testEntityManager.clear()

            val actual = repository.findById(request.id)

            assertEquals(request, actual)
        }

        @Test
        fun `location should not be found by id`() {
            val actual = repository.findById(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `location should be updated`() {
            val region = regionRepository.add(randomRegion())
            val timezone = timezoneRepository.add(randomTimezone())
            val request = randomLocation(region = region, timezone = timezone)
            repository.add(request)

            val updated = request.copy(name = randomString())

            testEntityManager.flush()
            testEntityManager.clear()

            val actual = repository.update(updated)

            assertEquals(updated, actual)
        }

        @Test
        fun `location should be deleted`() {
            val region = regionRepository.add(randomRegion())
            val timezone = timezoneRepository.add(randomTimezone())
            val request = randomLocation(region = region, timezone = timezone)
            repository.add(request)

            repository.delete(request.id)

            assertNull(repository.findById(request.id))
        }
    }