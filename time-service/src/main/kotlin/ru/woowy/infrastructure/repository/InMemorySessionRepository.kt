package ru.woowy.infrastructure.repository

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.SessionState
import ru.woowy.domain.repository.SessionRepository
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Repository
internal class InMemorySessionRepository : SessionRepository {
    private val sessions: ConcurrentMap<UUID, SessionState> = ConcurrentHashMap()

    override fun addSession(
        worldId: UUID,
        state: SessionState,
    ) {
        sessions[worldId] = state
    }

    override fun updateSession(
        worldId: UUID,
        state: SessionState,
    ) {
        sessions[worldId] = state
    }

    override fun getSessions(): ConcurrentMap<UUID, SessionState> = sessions

    override fun getSession(worldId: UUID): SessionState? = sessions[worldId]

    override fun deleteSession(worldId: UUID) {
        sessions.remove(worldId)
    }
}