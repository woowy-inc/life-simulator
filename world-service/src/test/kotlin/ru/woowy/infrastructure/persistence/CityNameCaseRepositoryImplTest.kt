package ru.woowy.infrastructure.persistence

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
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

internal class CityNameCaseRepositoryImplTest
    @Autowired
    constructor(
        private val jpaCityRepository: JpaCityRepository,
        private val jpaRegionRepository: JpaRegionRepository,
        private val jpaTimezoneRepository: JpaTimezoneRepository,
        private val jpaCityNameCaseRepository: JpaCityNameCaseRepository,
    ) : JpaRepositoryTest() {
        private val regionRepository = RegionRepositoryImpl(jpaRegionRepository)
        private val timezoneRepository = TimezoneRepositoryImpl(jpaTimezoneRepository)
        private val cityRepository = CityRepositoryImpl(jpaCityRepository, jpaRegionRepository, jpaTimezoneRepository)
        private val repository = CityNameCaseRepositoryImpl(jpaCityNameCaseRepository, jpaCityRepository)

        @BeforeEach
        fun setUp() {
            jpaCityNameCaseRepository.deleteAll()
            jpaCityRepository.deleteAll()
            jpaRegionRepository.deleteAll()
            jpaTimezoneRepository.deleteAll()
        }

        private fun savedCity() = cityRepository.add(
            randomCity(
                region = regionRepository.add(randomRegion(nameCase = null)),
                timezone = timezoneRepository.add(randomTimezone()),
                nameCase = null,
            ),
        )

        @Test
        fun `name case should be added`() {
            val city = savedCity()
            val request = randomCityNameCase(cityId = city.id)

            val actual = repository.add(request)

            assertEquals(request, actual)
        }

        @Test
        fun `name case should be found by city id`() {
            val city = savedCity()
            val request = randomCityNameCase(cityId = city.id)
            repository.add(request)

            val actual = repository.findAllByCityId(city.id)

            assertEquals(listOf(request), actual)
        }

        @Test
        fun `name cases should not be found by city id`() {
            val actual = repository.findAllByCityId(randomUUID())

            assertTrue(actual.isEmpty())
        }

        @Test
        fun `name case should be deleted by city id`() {
            val city = savedCity()
            repository.add(randomCityNameCase(cityId = city.id))

            repository.deleteAll(city.id)

            assertTrue(repository.findAllByCityId(city.id).isEmpty())
        }
    }