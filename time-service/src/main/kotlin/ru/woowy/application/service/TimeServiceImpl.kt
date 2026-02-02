package ru.woowy.application.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Service
import ru.woowy.application.SessionManager
import ru.woowy.application.usecase.AddSessionUseCase
import ru.woowy.application.usecase.GetSessionUseCase
import ru.woowy.application.usecase.UpdateSessionUseCase
import ru.woowy.domain.model.SessionState
import ru.woowy.domain.service.TimeService
import ru.woowy.extension.badRequest
import ru.woowy.extension.notFound
import ru.woowy.game.GameConfig
import ru.woowy.infrastructure.messaging.KafkaEventPublisher
import java.time.Duration
import java.time.Instant
import java.util.UUID

@Service
internal class TimeServiceImpl(
    private val addSessionUseCase: AddSessionUseCase,
    private val sessionManager: SessionManager,
    private val getSessionUseCase: GetSessionUseCase,
    private val updateSessionUseCase: UpdateSessionUseCase,
    private val kafkaEventPublisher: KafkaEventPublisher<*, *>,
) : TimeService,
    DisposableBean {
    private val logger = LoggerFactory.getLogger(TimeServiceImpl::class.java)

    override fun startTime(
        worldId: UUID,
        config: GameConfig,
    ) {
        val startTime = Instant.now()
        val session = getSessionUseCase(worldId)

        val state =
            if (session != null && session.isPaused) {
                SessionState(
                    config = session.config,
                    startTime = startTime,
                    pausedAt = null,
                    accumulatedTime = session.accumulatedTime,
                )
            } else {
                SessionState(
                    config = config,
                    startTime = startTime,
                )
            }

        if (session != null) {
            updateSessionUseCase(worldId, state)
        } else {
            addSessionUseCase(worldId, state)
        }

        val currentTime = state.getCurrentTime(startTime)

        sessionManager.startSession(config, worldId, startTime, currentTime) { event ->
            kafkaEventPublisher.publish(event)
            logger.info("Event: $event")
        }

        logger.info("Started time for world[$worldId], game time: $currentTime")
    }

    override fun stopTime(worldId: UUID) {
        val session =
            getSessionUseCase(worldId)
                ?: notFound("Session with world[$worldId] not found")

        if (session.isPaused) {
            badRequest("Session with world[$worldId] is already stopped")
        }

        sessionManager.cancelSession(worldId)

        val now = Instant.now()
        val currentGameTime = session.getCurrentTime(now)

        val state =
            SessionState(
                config = session.config,
                startTime = session.startTime,
                pausedAt = now,
                accumulatedTime = session.accumulatedTime + Duration.between(session.startTime, now),
            )

        updateSessionUseCase(worldId, state)

        logger.info("Stopped session for world[$worldId], game time: $currentGameTime")
    }

    override fun destroy() {
        logger.info("Shutting down, stopping all sessions")
        sessionManager.destroy()
        logger.info("All sessions stopped")
    }
}