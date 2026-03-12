package ru.woowy.infrastructure.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.model.CharacterGameSettings
import ru.woowy.domain.model.CharacterGameSettingsDto
import ru.woowy.domain.model.UserPrincipal
import ru.woowy.domain.usecase.CharacterGameSettingsUseCase
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.mapper.asDto
import ru.woowy.infrastructure.web.RestEndpoint

@RestController
@RequestMapping(RestEndpoint.BASE_URL)
class CharacterGameSettingsController(
    private val characterGameSettingsUseCase: CharacterGameSettingsUseCase,
) {
    @GetMapping("/{id}${RestEndpoint.GAME_SETTINGS}")
    fun getGameSettings(
        @PathVariable id: CharacterId,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<CharacterGameSettingsDto> =
        ResponseEntity.ok(characterGameSettingsUseCase.get(id, principal.userId)?.asDto())

    @PostMapping(RestEndpoint.GAME_SETTINGS)
    fun addOrUpdateGameSettings(
        @RequestBody request: CharacterGameSettings,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<CharacterGameSettingsDto> =
        ResponseEntity.ok(characterGameSettingsUseCase.addOrUpdate(request, principal.userId).asDto())

    @DeleteMapping(RestEndpoint.GAME_SETTINGS)
    fun deleteGameSettings(
        @RequestParam characterId: CharacterId,
        @AuthenticationPrincipal principal: UserPrincipal,
    ) = characterGameSettingsUseCase.delete(characterId, principal.userId)
}