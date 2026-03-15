package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.client.CharacterServiceClient
import ru.woowy.domain.model.GameSession
import ru.woowy.domain.model.GameSessionRequest
import ru.woowy.domain.model.GameStatus
import ru.woowy.domain.repository.GameSessionRepository
import ru.woowy.domain.usecase.GameSessionUseCase
import ru.woowy.extension.notFound
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId
import java.time.LocalDateTime

@Service
class GameSessionUseCaseImpl(
    private val gameSessionRepository: GameSessionRepository,
    private val characterServiceClient: CharacterServiceClient,
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

    override fun start(characterId: CharacterId): GameSession? {
        val found = get(characterId) ?: notFound(SESSION_NOT_FOUND)
        val session =
            found.copy(
                status = GameStatus.ACTIVE,
                pausedAt = null,
            )

        return gameSessionRepository.update(session)
    }

    override fun pause(characterId: CharacterId): GameSession? {
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