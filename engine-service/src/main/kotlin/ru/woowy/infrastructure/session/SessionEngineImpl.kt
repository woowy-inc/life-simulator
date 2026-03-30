package ru.woowy.infrastructure.session

import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import ru.woowy.domain.client.CharacterServiceClient
import ru.woowy.domain.messaging.EventPublisher
import ru.woowy.domain.model.GameSession
import ru.woowy.domain.model.GameSessionContext
import ru.woowy.domain.repository.GameSessionRepository
import ru.woowy.domain.session.SessionEngine
import ru.woowy.extension.classLogger
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.lifecycle.SessionScope
import ru.woowy.infrastructure.mapper.asWorldTickEvent

@Component
class SessionEngineImpl(
    private val characterServiceClient: CharacterServiceClient,
    private val gameSessionRepository: GameSessionRepository,
    private val eventPublisher: EventPublisher,
    private val sessionScope: SessionScope,
) : SessionLooper(sessionScope),
    SessionEngine {
    private val logger = classLogger()

    companion object {
        const val GAME_SETTINGS_NOT_FOUND = "Game settings not found"
    }

    override suspend fun startSimulation(
        characterId: CharacterId,
        session: GameSession,
    ): Boolean {
        val settings =
            characterServiceClient
                .getGameSettings(characterId)
                ?: run {
                    logger.error(GAME_SETTINGS_NOT_FOUND)
                    return false
                }

        val context = GameSessionContext(characterId, session, settings)

        return start(context) { sessionSnapshot ->
            with(sessionScope) {
                launch { gameSessionRepository.update(sessionSnapshot) }
                launch { eventPublisher.publish(sessionSnapshot.asWorldTickEvent(settings.speed)) }
            }
        }
    }

    override suspend fun stopSimulation(characterId: CharacterId): Boolean = stop(characterId)
}