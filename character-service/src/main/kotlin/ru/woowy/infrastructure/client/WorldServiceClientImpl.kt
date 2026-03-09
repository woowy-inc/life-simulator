package ru.woowy.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.woowy.domain.client.WorldServiceClient
import ru.woowy.domain.model.LocationDto
import ru.woowy.id.LocationId
import ru.woowy.security.Service

@FeignClient(name = Service.WORLD_SERVICE)
interface WorldServiceClientImpl : WorldServiceClient {
    companion object {
        private const val LOCATION_BY_ID = "/world/location/{id}"
    }

    @GetMapping(LOCATION_BY_ID)
    override fun getLocation(
        @PathVariable id: LocationId,
    ): LocationDto?
}