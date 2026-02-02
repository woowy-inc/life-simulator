package ru.woowy.presentation.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.character.Character
import ru.woowy.domain.usecase.CharacterUseCase

private const val BASE_ENDPOINT = "/character"

@RestController
internal class CharacterController(
    private val characterUseCase: CharacterUseCase,
) {
    @PostMapping(BASE_ENDPOINT)
    fun createCharacter(): ResponseEntity<Character> = ResponseEntity.ok(characterUseCase.createCharacter())

    @GetMapping(BASE_ENDPOINT)
    fun getCharacter(): ResponseEntity<Character> = ResponseEntity.ok(characterUseCase.getCharacter())
}