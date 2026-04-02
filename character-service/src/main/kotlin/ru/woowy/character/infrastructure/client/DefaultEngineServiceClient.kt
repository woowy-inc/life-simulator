package ru.woowy.character.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.woowy.character.domain.client.EngineServiceClient
import ru.woowy.character.domain.model.GamePreview
import ru.woowy.id.CharacterId
import ru.woowy.security.Service

@FeignClient(name = Service.ENGINE_SERVICE)
interface DefaultEngineServiceClient : EngineServiceClient {
    companion object {
        private const val SESSION_BY_CHARACTER_ID = "/session"
    }

    @GetMapping("$SESSION_BY_CHARACTER_ID/{characterId}")
    override fun getGame(
        @PathVariable characterId: CharacterId,
    ): GamePreview?
}