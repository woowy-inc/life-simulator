package ru.woowy.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.Character
import ru.woowy.domain.repository.CharacterRepository
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.persistence.entity.CharacterEntity
import ru.woowy.infrastructure.persistence.jpa.CharacterJpaRepository
import kotlin.jvm.optionals.getOrNull

@Repository
class CharacterRepositoryImpl(
    private val characterJpaRepository: CharacterJpaRepository,
) : CharacterRepository {
    override fun add(character: Character): Character = characterJpaRepository
        .save(character.asEntity())
        .asDomain()

    override fun findByCharacter(characterId: CharacterId): Character? = characterJpaRepository
        .findById(characterId)
        .getOrNull()
        ?.asDomain()

    override fun findAllByUser(userId: UserId): List<Character> = characterJpaRepository
        .findAllByUserId(userId)
        .asDomain()

    override fun update(character: Character): Character = characterJpaRepository
        .save(character.asEntity())
        .asDomain()

    override fun delete(characterId: CharacterId) = characterJpaRepository.deleteById(characterId)

    private fun Character.asEntity() = CharacterEntity(
        id = id,
        userId = userId,
        name = name,
        gender = gender,
        birthday = birthday,
        locationId = locationId,
        worldId = worldId,
        createdAt = createdAt,
    )
}