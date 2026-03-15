package ru.woowy.infrastructure.session

import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import ru.woowy.domain.model.GameSession
import ru.woowy.domain.model.GameSettings
import ru.woowy.game.GameConfig
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.lifecycle.SessionScope

@Component
class SessionEngine(
    private val sessionScope: SessionScope,
) {
    val activeSessions = ConcurrentHashMap<CharacterId, Job>()

    /**
     * Initiates a continuous game loop for a character if a session is not already active.
     * The loop increments game time and triggers updates based on the configured tick interval.
     *
     * @param characterId Unique identifier for the character.
     * @param session Initial state of the game session.
     * @param settings Configuration governing session progression.
     * @param onTick Callback executed on every session update.
     * @return True if the session was successfully started, false if a session already exists.
     */
    fun start(
        characterId: CharacterId,
        session: GameSession,
        settings: GameSettings,
        onTick: (GameSession) -> Unit,
    ): Boolean {
        val job =
            sessionScope.launch(start = CoroutineStart.LAZY) {
                var current = session

                while (isActive) {
                    current =
                        current.copy(
                            tickNumber = current.tickNumber + 1,
                            gameTime = current.gameTime.plusMinutes(settings.speed.gameMinutesPerTick),
                        )

                    onTick(current)

                    delay(GameConfig.TICK_INTERVAL_SECONDS.seconds)
                }
            }

        if (activeSessions.putIfAbsent(characterId, job) != null) {
            job.cancel()
            return false
        }

        job.start()
        return true
    }

    /**
     * Terminates and removes an active game session for the specified character.
     * This method cancels the underlying coroutine job to stop all background processing.
     *
     * @param characterId Unique identifier of the character whose session should be terminated.
     * @return True if a session was found and stopped, false otherwise.
     */
    fun stop(characterId: CharacterId): Boolean {
        val job = activeSessions.remove(characterId) ?: return false
        job.cancel()

        return true
    }
}