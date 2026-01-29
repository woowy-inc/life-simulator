package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.domain.repository.SessionRepository

@Service
internal class GetSessionsUseCase(
    private val sessionRepository: SessionRepository,
) {
    operator fun invoke() = sessionRepository.getSessions()
}