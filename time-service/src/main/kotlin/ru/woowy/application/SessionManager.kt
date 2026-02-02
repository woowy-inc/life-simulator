package ru.woowy.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Component
import ru.woowy.application.config.AppProperties
import ru.woowy.domain.Event
import ru.woowy.game.GameConfig
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

private const val MAX_ATTEMPTS = 5

@Component
internal class SessionManager(
    private val appProperties: AppProperties,
) : DisposableBean {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val logger = LoggerFactory.getLogger(SessionManager::class.java)

    private val jobs = ConcurrentHashMap<UUID, Job>()

    fun startSession(
        config: GameConfig,
        worldId: UUID,
        startTime: Instant,
        currentTime: Instant,
        onTick: (Event) -> Unit,
    ) = scope
        .launch {
            val gameHours = config.timeScale.gameHoursPerRealSecond
            var retry = 0
            var time = currentTime

            while (isActive && retry <= MAX_ATTEMPTS) {
                time = time.plus(gameHours, ChronoUnit.HOURS)

                try {
                    val event =
                        Event
                            .WorldTickEvent(
                                UUID.randomUUID(),
                                worldId,
                                config,
                                startTime,
                                time,
                            )

                    onTick(event)
                } catch (e: Exception) {
                    logger.error("Error in world $worldId tick", e)
                    retry++
                }

                delay(appProperties.tickRate)
            }
        }.also { jobs[worldId] = it }

    fun isSessionActive(worldId: UUID): Boolean = jobs.containsKey(worldId) && jobs[worldId]?.isActive == true

    fun cancelSession(worldId: UUID) {
        jobs[worldId]?.cancel()
    }

    override fun destroy() {
        jobs.clear()
        scope.cancel()
    }
}