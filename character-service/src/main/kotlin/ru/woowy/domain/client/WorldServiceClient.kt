package ru.woowy.domain.client

import ru.woowy.domain.model.LocationDto
import ru.woowy.id.LocationId

interface WorldServiceClient {
    fun getLocation(id: LocationId): LocationDto?
}