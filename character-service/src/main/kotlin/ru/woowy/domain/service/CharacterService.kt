package ru.woowy.domain.service

import ru.woowy.character.Character

internal interface CharacterService {
    fun generateCharacter(): Character
}