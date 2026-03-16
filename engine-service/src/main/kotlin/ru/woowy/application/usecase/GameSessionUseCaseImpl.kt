package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.client.CharacterServiceClient
import ru.woowy.domain.model.GameSession
import ru.woowy.domain.model.GameSessionRequest
import ru.woowy.domain.model.GameStatus
import ru.woowy.domain.repository.GameSessionRepository
import ru.woowy.domain.session.SessionEngine
import ru.woowy.domain.usecase.GameSessionUseCase
import ru.woowy.extension.notFound
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId
import java.time.LocalDateTime

@Service
class GameSessionUseCaseImpl(
    private val gameSessionRepository: GameSessionRepository,
    private val characterServiceClient: CharacterServiceClient,
    private val sessionEngine: SessionEngine,
) : GameSessionUseCase {
    companion object {
        const val CHARACTER_NOT_FOUND = "Character not found"
        const val SESSION_NOT_FOUND = "Session not found"
    }

    override fun get(characterId: CharacterId): GameSession? = gameSessionRepository.findById(characterId)

    override fun create(
        request: GameSessionRequest,
        startedBy: UserId,
    ): GameSession {
        val now = LocalDateTime.now()

        val character =
            characterServiceClient
                .getCharacter(request.characterId)
                ?: notFound(CHARACTER_NOT_FOUND)

        val session =
            GameSession(
                characterId = request.characterId,
                status = GameStatus.INACTIVE,
                gameTime = character.birthday,
                startedAt = now,
                startedBy = startedBy,
                tickNumber = 0L,
                pausedAt = now,
            )

        return gameSessionRepository.add(session)
    }

    override fun update(session: GameSession): GameSession? = gameSessionRepository.update(session)

    override suspend fun start(
        characterId: CharacterId,
        startedBy: UserId,
    ): GameSession {
        val found = get(characterId) ?: create(GameSessionRequest(characterId), startedBy)
        val session =
            found.copy(
                status = GameStatus.ACTIVE,
                pausedAt = null,
            )

        val activeSession =
            gameSessionRepository
                .update(session)
                ?: notFound(SESSION_NOT_FOUND)

        sessionEngine.startSimulation(characterId, activeSession)

        return activeSession
    }

    override suspend fun stop(characterId: CharacterId): GameSession? {
        sessionEngine.stopSimulation(characterId)

        val found = get(characterId) ?: notFound(SESSION_NOT_FOUND)
        val session =
            found.copy(
                status = GameStatus.INACTIVE,
                pausedAt = LocalDateTime.now(),
            )

        return gameSessionRepository.update(session)
    }

    override fun delete(characterId: CharacterId) = gameSessionRepository.delete(characterId)
}