package ru.woowy.infrastructure.importer

import kotlinx.coroutines.launch
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import ru.woowy.domain.model.LocationImport
import ru.woowy.domain.usecase.LocationUseCase
import ru.woowy.domain.usecase.RegionUseCase
import ru.woowy.domain.usecase.TimezoneUseCase
import ru.woowy.extension.classLogger
import ru.woowy.infrastructure.lifecycle.SchedulerScope
import ru.woowy.infrastructure.mapper.asDomain
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.readValue

@Service
class LocationImporter(
    private val resourceLoader: ResourceLoader,
    private val schedulerScope: SchedulerScope,
    private val regionUseCase: RegionUseCase,
    private val timezoneUseCase: TimezoneUseCase,
    private val locationUseCase: LocationUseCase,
    private val mapper: ObjectMapper,
) : ApplicationRunner {
    private val logger = classLogger()

    override fun run(args: ApplicationArguments) {
        if (locationUseCase.isEmpty()) {
            logger.info("Locations is empty, starting locations import...")
            importLocations()
        } else {
            logger.info("Locations already loaded, skipping import...")
        }
    }

    private fun importLocations() = schedulerScope.launch {
        val stream = resourceLoader.getResource("classpath:data/regions.json").inputStream
        val data = mapper.readValue<Array<LocationImport>>(stream)

        val timezones = importTimezones(data)
        val regions = importRegions(data)
        val cities = importCities(data)

        logger.info("Locations import complete: $timezones timezones, $regions regions, $cities cities")
    }

    private fun importTimezones(data: Array<LocationImport>): Int {
        logger.info("Import timezones...")

        return timezoneUseCase
            .import(data.map { it.timezone.asDomain() }.distinctBy { it.timezoneId })
            .count()
            .also { count ->
                logger.info("Imported $count timezones")
            }
    }

    private fun importRegions(data: Array<LocationImport>): Int {
        logger.info("Import regions...")

        return regionUseCase
            .import(data.map { it.region.asDomain() }.distinctBy { it.id })
            .count()
            .also { count ->
                logger.info("Imported $count regions")
            }
    }

    private fun importCities(data: Array<LocationImport>): Int {
        logger.info("Import cities...")

        return locationUseCase
            .import(data.map { it.asDomain() })
            .count()
            .also { count ->
                logger.info("Imported $count cities")
            }
    }
}