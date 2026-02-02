package ru.woowy.application.service

import org.springframework.stereotype.Service
import ru.woowy.character.Character
import ru.woowy.domain.service.CharacterService

@Service
internal class CharacterServiceImpl : CharacterService {
    override fun generateCharacter(): Character {
        TODO()
    }
}