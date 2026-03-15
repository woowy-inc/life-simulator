package ru.woowy.infrastructure.service

import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import ru.woowy.domain.client.CharacterServiceClient
import ru.woowy.domain.messaging.EventPublisher
import ru.woowy.domain.model.GameSession
import ru.woowy.domain.repository.GameSessionRepository
import ru.woowy.domain.service.EngineService
import ru.woowy.extension.classLogger
import ru.woowy.extension.notFound
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.lifecycle.ServiceScope
import ru.woowy.infrastructure.mapper.asWorldTickEvent
import ru.woowy.infrastructure.session.SessionEngine

@Service
class EngineServiceImpl(
    private val sessionEngine: SessionEngine,
    private val characterServiceClient: CharacterServiceClient,
    private val gameSessionRepository: GameSessionRepository,
    private val eventPublisher: EventPublisher,
    private val serviceScope: ServiceScope,
) : EngineService {
    private val logger = classLogger()

    companion object {
        const val GAME_SETTINGS_NOT_FOUND = "Game settings not found"
    }

    override suspend fun startSimulation(
        characterId: CharacterId,
        session: GameSession,
    ): Boolean {
        val settings = characterServiceClient.getGameSettings(characterId) ?: notFound(GAME_SETTINGS_NOT_FOUND)

        return sessionEngine.start(characterId, session, settings) { tick ->
            logger.info(tick.toString())

            with(serviceScope) {
                launch { gameSessionRepository.update(tick) } // TODO save to redis and on N times save to DB }
                launch { eventPublisher.publish(tick.asWorldTickEvent()) }
            }
        }
    }

    override suspend fun stopSimulation(characterId: CharacterId): Boolean = sessionEngine.stop(characterId)
}