package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.character.Character
import ru.woowy.domain.usecase.CharacterUseCase

@Service
class CharacterUseCaseImpl : CharacterUseCase {
    override fun createCharacter(): Character {
        TODO("Not yet implemented")
    }

    override fun getCharacter(): Character {
        TODO("Not yet implemented")
    }
}