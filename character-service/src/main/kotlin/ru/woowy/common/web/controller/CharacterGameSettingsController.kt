package ru.woowy.common.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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
import ru.woowy.character.domain.model.CharacterGameSettings
import ru.woowy.character.domain.model.CharacterGameSettingsDto
import ru.woowy.character.domain.usecase.CharacterGameSettingsUseCase
import ru.woowy.character.infrastructure.mapper.asDto
import ru.woowy.common.web.RestEndpoint
import ru.woowy.domain.model.UserPrincipal
import ru.woowy.id.CharacterId

@Tag(name = "Character Game Settings")
@RestController
@RequestMapping(RestEndpoint.BASE_URL)
class CharacterGameSettingsController(
    private val characterGameSettingsUseCase: CharacterGameSettingsUseCase,
) {
    @Operation(summary = "Get character game settings")
    @GetMapping("/{id}${RestEndpoint.GAME_SETTINGS}")
    fun getGameSettings(
        @PathVariable id: CharacterId,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<CharacterGameSettingsDto> =
        ResponseEntity.ok(characterGameSettingsUseCase.get(id, principal.userId)?.asDto())

    @Operation(summary = "Add or update character game settings")
    @PostMapping(RestEndpoint.GAME_SETTINGS)
    fun addOrUpdateGameSettings(
        @RequestBody request: CharacterGameSettings,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<CharacterGameSettingsDto> =
        ResponseEntity.ok(characterGameSettingsUseCase.addOrUpdate(request, principal.userId).asDto())

    @Operation(summary = "Delete character game settings")
    @DeleteMapping(RestEndpoint.GAME_SETTINGS)
    fun deleteGameSettings(
        @RequestParam characterId: CharacterId,
        @AuthenticationPrincipal principal: UserPrincipal,
    ) = characterGameSettingsUseCase.delete(characterId, principal.userId)
}