package ru.woowy.domain.repository

import ru.woowy.domain.model.SessionState
import java.util.UUID
import java.util.concurrent.ConcurrentMap

internal interface SessionRepository {
    fun addSession(
        worldId: UUID,
        state: SessionState,
    )

    fun updateSession(
        worldId: UUID,
        state: SessionState,
    )

    fun getSessions(): ConcurrentMap<UUID, SessionState>

    fun getSession(worldId: UUID): SessionState?

    fun deleteSession(worldId: UUID)
}