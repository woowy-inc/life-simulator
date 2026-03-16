package ru.woowy.character.domain.client

import ru.woowy.character.domain.model.LocationDto
import ru.woowy.id.LocationId

interface WorldServiceClient {
    fun getLocation(id: LocationId): LocationDto?
}