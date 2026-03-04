package ru.woowy.infrastructure.persistence

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager
import ru.woowy.helper.randomCity
import ru.woowy.helper.randomCityNameCase
import ru.woowy.helper.randomRegion
import ru.woowy.helper.randomTimezone
import ru.woowy.infrastructure.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.JpaCityNameCaseRepository
import ru.woowy.infrastructure.persistence.jpa.JpaCityRepository
import ru.woowy.infrastructure.persistence.jpa.JpaRegionRepository
import ru.woowy.infrastructure.persistence.jpa.JpaTimezoneRepository
import ru.woowy.util.randomUUID

class CityRepositoryImplTest
    @Autowired
    constructor(
        private val jpaCityRepository: JpaCityRepository,
        private val jpaRegionRepository: JpaRegionRepository,
        private val jpaTimezoneRepository: JpaTimezoneRepository,
        private val jpaCityNameCaseRepository: JpaCityNameCaseRepository,
        private val testEntityManager: TestEntityManager,
    ) : JpaRepositoryTest() {
        private val regionRepository = RegionRepositoryImpl(jpaRegionRepository)
        private val timezoneRepository = TimezoneRepositoryImpl(jpaTimezoneRepository)
        private val cityNameCaseRepository = CityNameCaseRepositoryImpl(jpaCityNameCaseRepository, jpaCityRepository)
        private val repository = CityRepositoryImpl(jpaCityRepository, jpaRegionRepository, jpaTimezoneRepository)

        @BeforeEach
        fun setUp() {
            jpaCityNameCaseRepository.deleteAll()
            jpaCityRepository.deleteAll()
            jpaRegionRepository.deleteAll()
            jpaTimezoneRepository.deleteAll()
        }

        @Test
        fun `city should be added`() {
            val region = regionRepository.add(randomRegion(nameCase = null))
            val timezone = timezoneRepository.add(randomTimezone())
            val request = randomCity(region = region, timezone = timezone, nameCase = null)

            val actual = repository.add(request)

            assertEquals(request, actual)
        }

        @Test
        fun `cities should be found`() {
            val region = regionRepository.add(randomRegion(nameCase = null))
            val timezone = timezoneRepository.add(randomTimezone())
            val request = randomCity(region = region, timezone = timezone, nameCase = null)
            repository.add(request)

            testEntityManager.flush()
            testEntityManager.clear()

            val actual = repository.findAll()

            assertEquals(listOf(request), actual)
        }

        @Test
        fun `city should be found by id`() {
            val region = regionRepository.add(randomRegion(nameCase = null))
            val timezone = timezoneRepository.add(randomTimezone())
            val request = randomCity(region = region, timezone = timezone, nameCase = null)
            repository.add(request)
            val nameCase = randomCityNameCase(cityId = request.id)
            cityNameCaseRepository.add(nameCase)

            testEntityManager.flush()
            testEntityManager.clear()

            val actual = repository.findById(request.id)

            assertEquals(request.copy(nameCase = nameCase), actual)
        }

        @Test
        fun `city should not be found by id`() {
            val actual = repository.findById(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `city should be updated`() {
            val region = regionRepository.add(randomRegion(nameCase = null))
            val timezone = timezoneRepository.add(randomTimezone())
            val request = randomCity(region = region, timezone = timezone, nameCase = null)
            repository.add(request)

            val updated = request.copy(name = ru.woowy.util.randomString())

            testEntityManager.flush()
            testEntityManager.clear()

            val actual = repository.update(updated)

            assertEquals(updated, actual)
        }

        @Test
        fun `city should be deleted`() {
            val region = regionRepository.add(randomRegion(nameCase = null))
            val timezone = timezoneRepository.add(randomTimezone())
            val request = randomCity(region = region, timezone = timezone, nameCase = null)
            repository.add(request)

            repository.delete(request.id)

            assertNull(repository.findById(request.id))
        }
    }