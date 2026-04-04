package ru.woowy.infrastructure.client.fallback

import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component
import ru.woowy.domain.client.CharacterServiceClient
import ru.woowy.domain.model.Character
import ru.woowy.domain.model.GameSettings
import ru.woowy.extension.classLogger
import ru.woowy.id.CharacterId

@Component
class CharacterServiceClientFallback : FallbackFactory<CharacterServiceClient> {
    private val logger = classLogger()

    override fun create(cause: Throwable?): CharacterServiceClient? {
        if (cause != null) {
            logger.error("Failure", cause)
        }

        return object : CharacterServiceClient {
            override fun getCharacter(id: CharacterId): Character? = null

            override fun getGameSettings(id: CharacterId): GameSettings? = null
        }
    }
}