package ru.woowy.application.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.woowy.domain.model.WorldState
import ru.woowy.domain.time.WorldTickEvent
import ru.woowy.domain.usecase.WorldUseCase
import ru.woowy.extension.forbidden
import ru.woowy.extension.notFound
import ru.woowy.game.GameConfig
import ru.woowy.game.Topic
import tools.jackson.databind.ObjectMapper
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

private const val DELAY_ONE_SECOND = 1_000L

@Service
internal class WorldUseCaseImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val mapper: ObjectMapper,
) : WorldUseCase,
    DisposableBean {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val logger = LoggerFactory.getLogger(WorldUseCaseImpl::class.java)
    private val worlds = ConcurrentHashMap<UUID, WorldState>()

    override fun startWorld(
        gameId: UUID,
        config: GameConfig,
    ) {
        if (worlds.containsKey(gameId)) {
            throw IllegalStateException("Game[$gameId] already exists")
        }

        val now = Instant.now()
        val job = launchWorld(gameId, config, now, now)

        worlds[gameId] =
            WorldState(
                config = config,
                startTime = now,
                currentTime = now,
                job = job,
                isPaused = false,
            )

        logger.info("Started game $gameId")
    }

    override fun pauseWorld(gameId: UUID) {
        val world = worlds[gameId] ?: notFound("Game[$gameId] not found")

        if (world.isPaused) {
            throw IllegalStateException("Game[$gameId] already paused")
        }

        world.job?.cancel()
        world.job = null
        world.isPaused = true

        logger.info("Paused game $gameId at ${world.currentTime}")
    }

    override fun resumeWorld(gameId: UUID) {
        val world = worlds[gameId] ?: notFound("Game[$gameId] not found")

        if (!world.isPaused) {
            forbidden("Game[$gameId] is not paused")
        }

        world.job =
            launchWorld(
                gameId = gameId,
                config = world.config,
                startTime = world.startTime,
                currentTime = world.currentTime,
            )
        world.isPaused = false

        logger.info("Resumed game $gameId from ${world.currentTime}")
    }

    override fun stopWorld(gameId: UUID) {
        val world = worlds.remove(gameId) ?: notFound("Game[$gameId] not found")
        world.job?.cancel()
        logger.info("Stopped game $gameId")
    }

    private fun launchWorld(
        gameId: UUID,
        config: GameConfig,
        startTime: Instant,
        currentTime: Instant,
    ) = scope.launch {
        var time = currentTime

        while (isActive) {
            time = time.plus(config.timeScale.gameHoursPerRealSecond, ChronoUnit.HOURS)
            worlds[gameId]?.currentTime = time

            try {
                val event = WorldTickEvent(config, startTime, time)
                kafkaTemplate
                    .send(
                        Topic.WORLD_TICK.title,
                        mapper.writeValueAsString(event),
                    ).get()

                logger.info("Game $gameId tick: $time")
            } catch (e: Exception) {
                logger.error("Error in game $gameId tick", e)
            }

            delay(DELAY_ONE_SECOND)
        }
    }

    override fun destroy() {
        logger.info("Shutting down, stopping ${worlds.size} games")
        worlds.values.forEach { it.job?.cancel() }
        scope.cancel()
    }
}