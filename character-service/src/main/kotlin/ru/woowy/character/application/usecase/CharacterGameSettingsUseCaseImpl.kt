package ru.woowy.character.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.character.domain.model.CharacterGameSettings
import ru.woowy.character.domain.repository.CharacterGameSettingsRepository
import ru.woowy.character.domain.repository.CharacterRepository
import ru.woowy.character.domain.usecase.CharacterGameSettingsUseCase
import ru.woowy.extension.forbidden
import ru.woowy.extension.notFound
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId

@Service
class CharacterGameSettingsUseCaseImpl(
    private val characterGameSettingsRepository: CharacterGameSettingsRepository,
    private val characterRepository: CharacterRepository,
) : CharacterGameSettingsUseCase {
    companion object {
        const val CHARACTER_NOT_FOUND = "Character not found"
        const val NOT_OWNER_ERROR = "Not owner of character"
    }

    override fun get(
        characterId: CharacterId,
        owner: UserId,
    ): CharacterGameSettings? {
        verifyOwner(characterId, owner)
        return characterGameSettingsRepository.findById(characterId)
    }

    override fun addOrUpdate(
        settings: CharacterGameSettings,
        owner: UserId,
    ): CharacterGameSettings {
        verifyOwner(settings.characterId, owner)
        return characterGameSettingsRepository.addOrUpdate(settings)
    }

    override fun delete(
        characterId: CharacterId,
        owner: UserId,
    ) {
        verifyOwner(characterId, owner)
        characterGameSettingsRepository.delete(characterId)
    }

    private fun verifyOwner(
        characterId: CharacterId,
        owner: UserId,
    ) {
        val character = characterRepository.findById(characterId) ?: notFound(CHARACTER_NOT_FOUND)

        if (character.userId != owner) {
            forbidden(NOT_OWNER_ERROR)
        }
    }
}