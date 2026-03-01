package ru.woowy.location.application.usecase.timezone

import org.springframework.stereotype.Service
import ru.woowy.location.domain.model.Timezone
import ru.woowy.location.domain.repository.TimezoneRepository

@Service
internal class UpdateTimezoneUseCase(
    private val timezoneRepository: TimezoneRepository,
) {
    operator fun invoke(timezone: Timezone): Timezone? = timezoneRepository.update(timezone)
}