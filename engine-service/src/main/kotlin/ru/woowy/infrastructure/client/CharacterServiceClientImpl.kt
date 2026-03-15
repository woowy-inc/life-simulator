package ru.woowy.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.woowy.domain.client.CharacterServiceClient
import ru.woowy.domain.model.Character
import ru.woowy.domain.model.GameSettings
import ru.woowy.id.CharacterId
import ru.woowy.security.Service

@FeignClient(name = Service.CHARACTER_SERVICE)
interface CharacterServiceClientImpl : CharacterServiceClient {
    companion object {
        private const val GET_CHARACTER = "/character/{id}"
        private const val GET_GAME_SETTINGS = "/character/{id}/game-settings"
    }

    @GetMapping(GET_CHARACTER)
    override fun getCharacter(
        @PathVariable id: CharacterId,
    ): Character?

    @GetMapping(GET_GAME_SETTINGS)
    override fun getGameSettings(
        @PathVariable id: CharacterId,
    ): GameSettings?
}