package ru.woowy.common.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.character.domain.model.Character
import ru.woowy.character.domain.model.CharacterRequest
import ru.woowy.character.domain.usecase.CharacterUseCase
import ru.woowy.common.web.RestEndpoint
import ru.woowy.domain.model.UserPrincipal
import ru.woowy.id.CharacterId
import java.util.UUID

@Tag(name = "Characters")
@RestController
@RequestMapping(RestEndpoint.BASE_URL)
class CharacterController(
    private val characterUseCase: CharacterUseCase,
) {
    @Operation(summary = "Create a new character")
    @PostMapping
    suspend fun createCharacter(
        @RequestBody request: CharacterRequest,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<Character> = ResponseEntity.ok(characterUseCase.create(request, principal.userId))

    @Operation(summary = "Get a character")
    @GetMapping("/{id}")
    suspend fun getCharacter(
        @PathVariable id: String,
    ): ResponseEntity<Character>? = ResponseEntity.ok(characterUseCase.get(UUID.fromString(id)))

    @Operation(summary = "Get all characters")
    @GetMapping
    suspend fun getCharacters(
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<List<Character>> = ResponseEntity.ok(characterUseCase.getAll(principal.userId))

    @Operation(summary = "Update a character")
    @PutMapping("/{id}")
    suspend fun updateCharacter(
        @PathVariable id: CharacterId,
        @RequestBody request: CharacterRequest,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<Character>? = ResponseEntity.ok(characterUseCase.update(id, request, principal.userId))

    @Operation(summary = "Delete a character")
    @DeleteMapping("/{id}")
    suspend fun deleteCharacter(
        @PathVariable id: CharacterId,
        @AuthenticationPrincipal principal: UserPrincipal,
    ) = ResponseEntity.ok(characterUseCase.delete(id, principal.userId))
}