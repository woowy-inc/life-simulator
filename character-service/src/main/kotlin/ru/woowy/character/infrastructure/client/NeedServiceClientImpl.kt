package ru.woowy.character.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import ru.woowy.character.domain.client.NeedServiceClient
import ru.woowy.character.domain.model.Need
import ru.woowy.id.CharacterId
import ru.woowy.security.Service

@FeignClient(name = Service.NEED_SERVICE)
interface NeedServiceClientImpl : NeedServiceClient {
    companion object {
        private const val NEEDS_BY_CHARACTER_IDS = "/internal/need/character"
    }

    @PostMapping(NEEDS_BY_CHARACTER_IDS)
    override fun getNeeds(
        @RequestBody characterIds: Array<CharacterId>,
    ): Map<CharacterId, Need>
}