package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.repository.SessionRepository
import java.util.UUID

@Service
internal class DeleteSessionUseCase(
    private val sessionRepository: SessionRepository,
) {
    operator fun invoke(worldId: UUID) {
        sessionRepository.deleteSession(worldId)
    }
}