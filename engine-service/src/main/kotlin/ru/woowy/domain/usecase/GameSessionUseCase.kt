package ru.woowy.domain.usecase

import ru.woowy.domain.model.GameSession
import ru.woowy.domain.model.GameSessionRequest
import ru.woowy.id.CharacterId
import ru.woowy.id.UserId

interface GameSessionUseCase {
    fun get(characterId: CharacterId): GameSession?

    fun create(
        request: GameSessionRequest,
        startedBy: UserId,
    ): GameSession

    fun start(characterId: CharacterId): GameSession?

    fun pause(characterId: CharacterId): GameSession?

    fun delete(characterId: CharacterId)
}