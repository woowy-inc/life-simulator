package ru.woowy.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.World
import ru.woowy.domain.repository.WorldRepository
import ru.woowy.id.CharacterId
import ru.woowy.id.WorldId
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.persistence.entity.WorldEntity
import ru.woowy.infrastructure.persistence.jpa.WorldJpaRepository
import kotlin.jvm.optionals.getOrNull

@Repository
class WorldRepositoryImpl(
    private val worldJpaRepository: WorldJpaRepository,
) : WorldRepository {
    override fun findById(worldId: WorldId): World? = worldJpaRepository.findById(worldId).getOrNull()?.asDomain()

    override fun findByCharacter(characterId: CharacterId): World? =
        worldJpaRepository.findFirstByCharacterId(characterId)?.asDomain()

    override fun add(world: World): World = worldJpaRepository.save(world.asEntity()).asDomain()

    override fun delete(worldId: WorldId) = worldJpaRepository.deleteById(worldId)

    override fun deleteByCharacter(characterId: CharacterId) = worldJpaRepository.deleteAllByCharacterId(characterId)

    private fun World.asEntity() = WorldEntity(
        id = this.id,
        characterId = this.characterId,
        createdAt = this.createdAt,
    )
}