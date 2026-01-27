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
import org.springframework.stereotype.Service
import ru.woowy.domain.DomainEvent
import ru.woowy.domain.model.WorldState
import ru.woowy.domain.usecase.TimeUseCase
import ru.woowy.extension.forbidden
import ru.woowy.extension.notFound
import ru.woowy.game.GameConfig
import ru.woowy.infrastructure.config.AppProperties
import ru.woowy.infrastructure.messaging.KafkaEventPublisher
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
internal class TimeUseCaseImpl(
    private val kafkaEventPublisher: KafkaEventPublisher<*, *>,
    private val appProperties: AppProperties,
) : TimeUseCase,
    DisposableBean {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val logger = LoggerFactory.getLogger(TimeUseCaseImpl::class.java)
    private val worlds = ConcurrentHashMap<UUID, WorldState>()

    override fun startTime(
        worldId: UUID,
        config: GameConfig,
    ) {
        if (worlds.containsKey(worldId)) {
            throw IllegalStateException("World[$worldId] already exists")
        }

        val now = Instant.now()
        val job = launchWorld(worldId, config, now, now)

        worlds[worldId] =
            WorldState(
                config = config,
                startTime = now,
                currentTime = now,
                job = job,
                isPaused = false,
            )

        logger.info("Started time for world $worldId")
    }

    override fun pauseTime(worldId: UUID) {
        val world = worlds[worldId] ?: notFound("World[$worldId] not found")

        if (world.isPaused) {
            throw IllegalStateException("World[$worldId] already paused")
        }

        world.job?.cancel()
        world.job = null
        world.isPaused = true

        logger.info("Paused world $worldId at ${world.currentTime}")
    }

    override fun resumeTime(worldId: UUID) {
        val world = worlds[worldId] ?: notFound("World[$worldId] not found")

        if (!world.isPaused) {
            forbidden("World[$worldId] is not paused")
        }

        world.job =
            launchWorld(
                worldId = worldId,
                config = world.config,
                startTime = world.startTime,
                currentTime = world.currentTime,
            )
        world.isPaused = false

        logger.info("Resumed world $worldId from ${world.currentTime}")
    }

    override fun stopTime(worldId: UUID) {
        val world = worlds.remove(worldId) ?: notFound("World[$worldId] not found")
        world.job?.cancel()
        logger.info("Stopped world $worldId")
    }

    private fun launchWorld(
        worldId: UUID,
        config: GameConfig,
        startTime: Instant,
        currentTime: Instant,
    ) = scope.launch {
        var time = currentTime

        while (isActive) {
            time = time.plus(config.timeScale.gameHoursPerRealSecond, ChronoUnit.HOURS)
            worlds[worldId]?.currentTime = time

            try {
                val event = DomainEvent.WorldTickEvent(worldId, config, startTime, time)
                kafkaEventPublisher.publish(event)

                logger.info("World $worldId tick: $time")
            } catch (e: Exception) {
                logger.error("Error in world $worldId tick", e)
            }

            delay(appProperties.tickRate)
        }
    }

    override fun destroy() {
        logger.info("Shutting down, stopping ${worlds.size} worlds")
        worlds.values.forEach { it.job?.cancel() }
        scope.cancel()
    }
}