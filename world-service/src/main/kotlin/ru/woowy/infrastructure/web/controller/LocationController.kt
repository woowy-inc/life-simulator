package ru.woowy.infrastructure.web.controller

import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.model.Location
import ru.woowy.domain.model.LocationId
import ru.woowy.domain.model.LocationPageable
import ru.woowy.domain.usecase.LocationUseCase
import ru.woowy.infrastructure.model.LocationSortColumn
import ru.woowy.infrastructure.web.RestEndpoint

@RestController
@RequestMapping(RestEndpoint.BASE_URL)
class LocationController(
    private val locationUseCase: LocationUseCase,
) {
    @GetMapping(RestEndpoint.LOCATION)
    fun getLocations(
        @RequestParam sortColumn: LocationSortColumn = LocationSortColumn.POPULATION,
        @RequestParam sortOrder: Sort.Direction = Sort.Direction.DESC,
        @RequestParam page: Int? = null,
        @RequestParam count: Int? = null,
        @RequestParam search: String? = null,
    ): ResponseEntity<LocationPageable> = ResponseEntity.ok(
        locationUseCase.getAll(sortColumn, sortOrder, search, page, count),
    )

    @GetMapping("${RestEndpoint.LOCATION}/{id}")
    fun getLocation(
        @PathVariable id: LocationId,
    ): ResponseEntity<Location>? = ResponseEntity.ok(locationUseCase.get(id))
}