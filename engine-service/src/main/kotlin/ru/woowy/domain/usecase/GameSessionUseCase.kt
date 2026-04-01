package ru.woowy.domain.usecase

import ru.woowy.domain.model.GameSession
import ru.woowy.domain.model.GameSessionRequest
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId

interface GameSessionUseCase {
    /**
     * Retrieves the current game session associated with the specified character.
     * Returns null if no active or stored session exists for the given ID.
     *
     * @param characterId Unique identifier of the character.
     * @return The found [GameSession], or null if not found.
     */
    fun get(characterId: CharacterId): GameSession?

    /**
     * Initializes a new game session record based on the provided request parameters.
     * This creates the session data in the system but does not necessarily start the simulation loop.
     *
     * @param request Data containing the initial configuration for the session.
     * @param startedBy Unique identifier of the user who initiated the creation.
     * @return The newly created [GameSession] instance.
     */
    fun create(
        request: GameSessionRequest,
        startedBy: UserId,
    ): GameSession

    /**
     * Updates an existing game session with new state or configuration data.
     * This ensures synchronization between the domain model and the persistence layer.
     *
     * @param session The [GameSession] instance containing updated information.
     * @return The updated [GameSession], or null if the session could not be found.
     */
    fun update(session: GameSession): GameSession?

    /**
     * Transitions a character's session to an active running state.
     * This triggers the underlying simulation engine to begin processing game ticks.
     *
     * @param characterId Unique identifier of the character whose session should start.
     * @param startedBy Unique identifier of the user who initiated the start.
     * @return The updated [GameSession] in its started state.
     */
    suspend fun start(
        characterId: CharacterId,
        startedBy: UserId,
    ): GameSession

    /**
     * Suspends the active simulation for the specified character.
     * The session data is preserved, but the background processing loop is halted.
     *
     * @param characterId Unique identifier of the character whose session should stop.
     * @return The [GameSession] in its stopped state, or null if no session was active.
     */
    suspend fun stop(characterId: CharacterId): GameSession?

    /**
     * Permanently removes the game session and all associated data for the character.
     * This operation stops any active simulation before performing the deletion.
     *
     * @param characterId Unique identifier of the character whose session data will be deleted.
     */
    suspend fun delete(characterId: CharacterId)
}