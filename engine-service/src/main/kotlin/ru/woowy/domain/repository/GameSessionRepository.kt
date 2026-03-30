package ru.woowy.domain.repository

import ru.woowy.domain.model.GameSession
import ru.woowy.id.CharacterId

interface GameSessionRepository {
    fun findById(characterId: CharacterId): GameSession?

    fun add(session: GameSession): GameSession

    fun update(session: GameSession): GameSession?

    fun delete(characterId: CharacterId)
}