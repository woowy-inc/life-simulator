package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.model.SessionState
import ru.woowy.domain.repository.SessionRepository
import java.util.UUID

@Service
internal class AddSessionUseCase(
    private val sessionRepository: SessionRepository,
) {
    operator fun invoke(
        worldId: UUID,
        state: SessionState,
    ) {
        sessionRepository.addSession(worldId, state)
    }
}