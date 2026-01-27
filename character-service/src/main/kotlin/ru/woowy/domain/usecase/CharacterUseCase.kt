package ru.woowy.domain.usecase

import ru.woowy.character.Character

internal interface CharacterUseCase {
    fun createCharacter(): Character

    fun getCharacter(): Character
}