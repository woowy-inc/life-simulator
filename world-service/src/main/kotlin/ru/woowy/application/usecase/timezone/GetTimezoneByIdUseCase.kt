package ru.woowy.application.usecase.timezone

import org.springframework.stereotype.Service
import ru.woowy.domain.model.Timezone
import ru.woowy.domain.model.TimezoneId
import ru.woowy.domain.repository.TimezoneRepository

@Service
internal class GetTimezoneByIdUseCase(
    private val timezoneRepository: TimezoneRepository,
) {
    operator fun invoke(timezoneId: TimezoneId): Timezone? = timezoneRepository.findByTimezoneId(timezoneId)
}