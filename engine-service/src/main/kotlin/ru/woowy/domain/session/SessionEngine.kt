package ru.woowy.domain.session

import ru.woowy.domain.model.GameSession
import ru.woowy.id.CharacterId

/**
 * Defines the contract for managing game simulation states for specific characters.
 * This service handles the lifecycle of character-driven logic within a game session.
 */
interface SessionEngine {
    /**
     * Initiates the simulation process for a specific character within the provided session.
     * This method prepares the character's state and begins executing scheduled game logic.
     *
     * @param characterId The unique identifier of the character to be simulated.
     * @param session The current active game session context.
     * @return True if the simulation started successfully, false otherwise.
     */
    suspend fun startSimulation(
        characterId: CharacterId,
        session: GameSession,
    ): Boolean

    /**
     * Terminates the active simulation for the specified character.
     * This ensures all resources related to the character's simulation are properly released.
     *
     * @param characterId The unique identifier of the character whose simulation should stop.
     * @return True if the simulation was successfully halted, false if no active simulation was found.
     */
    suspend fun stopSimulation(characterId: CharacterId): Boolean
}