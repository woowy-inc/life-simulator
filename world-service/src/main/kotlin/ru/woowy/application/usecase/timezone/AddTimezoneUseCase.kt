package ru.woowy.application.usecase.timezone

import org.springframework.stereotype.Service
import ru.woowy.domain.model.Timezone
import ru.woowy.domain.repository.TimezoneRepository

@Service
internal class AddTimezoneUseCase(
    private val timezoneRepository: TimezoneRepository,
) {
    operator fun invoke(timezone: Timezone): Timezone = timezoneRepository.add(timezone)
}