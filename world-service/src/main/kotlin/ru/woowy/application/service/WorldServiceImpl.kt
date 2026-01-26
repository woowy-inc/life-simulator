package ru.woowy.application.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.woowy.domain.model.WorldTickJob
import ru.woowy.domain.service.WorldService
import ru.woowy.domain.time.WorldTickEvent
import ru.woowy.extension.notFound
import ru.woowy.game.GameConfig
import ru.woowy.game.Topic
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.jacksonObjectMapper
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
internal class WorldServiceImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val mapper: ObjectMapper = jacksonObjectMapper(),
) : WorldService {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val logger: Logger = LoggerFactory.getLogger(WorldServiceImpl::class.java)
    private val worlds = mutableMapOf<UUID, WorldTickJob>()

    override fun startWorld(
        gameId: UUID,
        config: GameConfig,
    ) {
        val now = Instant.now()
        val gameId = UUID.randomUUID()
        val world = launchWorld(config, now, gameId)

        worlds[gameId] = WorldTickJob(config, now, world)

        logger.info("Starting game with config [$config]")
    }

    override fun pauseWorld(gameId: UUID) {
        val world = worlds[gameId] ?: notFound("Game[$gameId] was not found")
        world.process.cancel()
    }

    override fun stopWorld(gameId: UUID) {
        val world = worlds[gameId] ?: notFound("Game[$gameId] was not found")
        world.process.cancel()
    }

    private fun launchWorld(
        config: GameConfig,
        now: Instant,
        gameId: UUID,
    ) = scope.launch {
        var currentTime = now

        while (isActive) {
            val hours = config.timeScale.gameHoursPerRealSecond.toLong()
            val delay = (1000 / hours)

            currentTime = currentTime.plus(1, ChronoUnit.HOURS)

            WorldTickEvent(config, now, currentTime)
                .run { kafkaTemplate.send(Topic.WORLD_TICK.title, mapper.writeValueAsString(this)) }

            logger.info("Game $gameId tick: $currentTime")
            delay(delay)
        }
    }
}