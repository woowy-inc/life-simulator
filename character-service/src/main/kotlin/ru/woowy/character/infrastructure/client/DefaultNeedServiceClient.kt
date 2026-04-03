package ru.woowy.character.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import ru.woowy.character.domain.client.NeedServiceClient
import ru.woowy.character.domain.model.NeedPreview
import ru.woowy.character.infrastructure.client.fallback.NeedServiceClientFallback
import ru.woowy.id.CharacterId
import ru.woowy.security.Service

@FeignClient(name = Service.NEED_SERVICE, fallbackFactory = NeedServiceClientFallback::class)
interface DefaultNeedServiceClient : NeedServiceClient {
    companion object {
        private const val NEED_BY_CHARACTER_ID = "/internal/need/character"
        private const val NEEDS_BY_CHARACTER_IDS = "/internal/need/character"
    }

    @GetMapping("$NEED_BY_CHARACTER_ID/{characterId}")
    override fun getNeed(
        @PathVariable characterId: CharacterId,
    ): NeedPreview?

    @PostMapping(NEEDS_BY_CHARACTER_IDS)
    override fun getNeeds(
        @RequestBody characterIds: Array<CharacterId>,
    ): Map<CharacterId, NeedPreview>
}