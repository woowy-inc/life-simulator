package ru.woowy.character.infrastructure.client.fallback

import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component
import ru.woowy.character.domain.client.NeedServiceClient
import ru.woowy.character.domain.model.NeedPreview
import ru.woowy.extension.classLogger
import ru.woowy.id.CharacterId

@Component
class NeedServiceClientFallback : FallbackFactory<NeedServiceClient> {
    private val logger = classLogger()

    override fun create(cause: Throwable?): NeedServiceClient? {
        logger.error("Failure", cause)

        return object : NeedServiceClient {
            override fun getNeed(characterId: CharacterId): NeedPreview? {
                logger.error("[getNeed] failure", cause)
                return null
            }

            override fun getNeeds(characterIds: Array<CharacterId>): Map<CharacterId, NeedPreview> {
                logger.error("[getNeeds] failure", cause)
                return emptyMap()
            }
        }
    }
}