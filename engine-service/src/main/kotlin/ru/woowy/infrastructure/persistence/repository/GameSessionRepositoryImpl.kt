package ru.woowy.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.GameSession
import ru.woowy.domain.repository.GameSessionRepository
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.persistence.entity.GameSessionEntity
import ru.woowy.infrastructure.persistence.jpa.GameSessionJpaRepository
import kotlin.jvm.optionals.getOrNull

@Repository
class GameSessionRepositoryImpl(
    private val gameSessionJpaRepository: GameSessionJpaRepository,
) : GameSessionRepository {
    override fun findById(characterId: CharacterId): GameSession? =
        gameSessionJpaRepository.findById(characterId).getOrNull()?.asDomain()

    override fun add(session: GameSession): GameSession = gameSessionJpaRepository.save(session.asEntity()).asDomain()

    override fun update(session: GameSession): GameSession? =
        gameSessionJpaRepository.save(session.asEntity()).asDomain()

    override fun delete(characterId: CharacterId) = gameSessionJpaRepository.deleteById(characterId)

    private fun GameSession.asEntity(): GameSessionEntity = GameSessionEntity(
        characterId = this.characterId,
        status = this.status,
        gameTime = this.gameTime,
        startedAt = this.startedAt,
        startedBy = this.startedBy,
        tickNumber = this.tickNumber,
        pausedAt = this.pausedAt,
    )
}