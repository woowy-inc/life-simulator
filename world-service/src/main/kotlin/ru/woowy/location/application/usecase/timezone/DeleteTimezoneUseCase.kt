package ru.woowy.location.application.usecase.timezone

import org.springframework.stereotype.Service
import ru.woowy.location.domain.model.TimezoneId
import ru.woowy.location.domain.repository.TimezoneRepository

@Service
internal class DeleteTimezoneUseCase(
    private val timezoneRepository: TimezoneRepository,
) {
    operator fun invoke(timezoneId: TimezoneId) = timezoneRepository.delete(timezoneId)
}