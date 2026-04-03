package ru.woowy.character.infrastructure.client.fallback

import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component
import ru.woowy.character.domain.client.WorldServiceClient
import ru.woowy.character.domain.model.LocationDto
import ru.woowy.extension.classLogger
import ru.woowy.id.LocationId

@Component
class WorldServiceClientFallback : FallbackFactory<WorldServiceClient> {
    private val logger = classLogger()

    override fun create(cause: Throwable?): WorldServiceClient? {
        logger.error("Failure", cause)

        return object : WorldServiceClient {
            override fun getLocation(id: LocationId): LocationDto? {
                logger.error("[getLocation] failure", cause)
                return null
            }
        }
    }
}