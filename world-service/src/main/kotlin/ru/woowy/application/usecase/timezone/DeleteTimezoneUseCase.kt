package ru.woowy.application.usecase.timezone

import org.springframework.stereotype.Service
import ru.woowy.domain.model.TimezoneId
import ru.woowy.domain.repository.TimezoneRepository

@Service
internal class DeleteTimezoneUseCase(
    private val timezoneRepository: TimezoneRepository,
) {
    operator fun invoke(timezoneId: TimezoneId) = timezoneRepository.delete(timezoneId)
}