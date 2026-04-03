package ru.woowy.character.infrastructure.client.fallback

import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component
import ru.woowy.character.domain.client.EngineServiceClient
import ru.woowy.character.domain.model.GamePreview
import ru.woowy.extension.classLogger
import ru.woowy.id.CharacterId

@Component
class EngineServiceClientFallback : FallbackFactory<EngineServiceClient> {
    private val logger = classLogger()

    override fun create(cause: Throwable?): EngineServiceClient? {
        logger.error("Failure", cause)

        return object : EngineServiceClient {
            override fun getGame(characterId: CharacterId): GamePreview? {
                logger.error("[getGame] failure", cause)
                return null
            }
        }
    }
}