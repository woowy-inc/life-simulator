package ru.woowy.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.Need
import ru.woowy.domain.repository.NeedRepository
import ru.woowy.id.CharacterId
import ru.woowy.id.NeedId
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.persistence.entity.NeedEntity
import ru.woowy.infrastructure.persistence.jpa.NeedJpaRepository
import java.util.UUID

@Repository
class NeedRepositoryImpl(
    private val needJpaRepository: NeedJpaRepository,
) : NeedRepository {
    override fun findLast(characterId: CharacterId): Need? = needJpaRepository.findLast(characterId)?.asDomain()

    override fun add(
        characterId: CharacterId,
        need: Need,
    ): Need = needJpaRepository.save(need.asEntity(characterId)).asDomain()

    override fun addAll(
        characterId: CharacterId,
        needs: List<Need>,
    ) {
        needJpaRepository.saveAll(needs.asEntity(characterId))
    }

    override fun update(
        characterId: CharacterId,
        need: Need,
    ): Need? = needJpaRepository.save(need.asEntity(characterId)).asDomain()

    override fun delete(needId: NeedId) = needJpaRepository.deleteById(needId)

    override fun deleteAll(characterId: CharacterId) = needJpaRepository.deleteAllByCharacterId(characterId)
}

private fun Need.asEntity(characterId: CharacterId): NeedEntity = NeedEntity(
    id = UUID.randomUUID(),
    characterId = characterId,
    hunger = this.hunger.value,
    sleep = this.sleep.value,
    body = this.body.value,
    mental = this.mental.value,
    social = this.social.value,
    health = this.health.value,
    happiness = this.happiness.value,
    createdAt = this.createdAt,
)

private fun Iterable<Need>.asEntity(characterId: CharacterId): Collection<NeedEntity> = this.map {
    it.asEntity(characterId)
}