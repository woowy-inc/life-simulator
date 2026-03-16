package ru.woowy.character.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import ru.woowy.character.domain.model.CharacterGameSettings
import ru.woowy.character.domain.repository.CharacterGameSettingsRepository
import ru.woowy.character.infrastructure.mapper.asDomain
import ru.woowy.character.infrastructure.persistence.entity.CharacterGameSettingsEntity
import ru.woowy.character.infrastructure.persistence.jpa.CharacterGameSettingsJpaRepository
import ru.woowy.id.CharacterId
import kotlin.jvm.optionals.getOrNull

@Repository
class CharacterGameSettingsRepositoryImpl(
    private val characterGameSettingsJpaRepository: CharacterGameSettingsJpaRepository,
) : CharacterGameSettingsRepository {
    override fun findById(characterId: CharacterId): CharacterGameSettings? =
        characterGameSettingsJpaRepository.findById(characterId).getOrNull()?.asDomain()

    override fun addOrUpdate(settings: CharacterGameSettings): CharacterGameSettings =
        characterGameSettingsJpaRepository.save(settings.asEntity()).asDomain()

    override fun delete(characterId: CharacterId) = characterGameSettingsJpaRepository.deleteById(characterId)

    fun CharacterGameSettings.asEntity() = CharacterGameSettingsEntity(
        characterId = this.characterId,
        speed = this.speed,
    )
}