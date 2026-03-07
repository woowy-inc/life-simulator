package ru.woowy.infrastructure.web.controller

import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.model.CityPageable
import ru.woowy.domain.usecase.CityUseCase
import ru.woowy.infrastructure.model.CitySortColumn
import ru.woowy.infrastructure.web.RestEndpoint

@RestController
@RequestMapping(RestEndpoint.BASE_URL)
class LocationController(
    private val cityUseCase: CityUseCase,
) {
    @GetMapping(RestEndpoint.LOCATION)
    fun getLocations(
        @RequestParam sortColumn: CitySortColumn = CitySortColumn.POPULATION,
        @RequestParam sortOrder: Sort.Direction = Sort.Direction.DESC,
        @RequestParam page: Int? = null,
        @RequestParam count: Int? = null,
        @RequestParam search: String? = null,
    ): ResponseEntity<CityPageable> = ResponseEntity.ok(
        cityUseCase.getAll(sortColumn, sortOrder, search, page, count),
    )
}