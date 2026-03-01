package ru.woowy.location.application.usecase.timezone

import org.springframework.stereotype.Service
import ru.woowy.location.domain.model.Timezone
import ru.woowy.location.domain.model.TimezoneId
import ru.woowy.location.domain.repository.TimezoneRepository

@Service
internal class GetTimezoneByIdUseCase(
    private val timezoneRepository: TimezoneRepository,
) {
    operator fun invoke(timezoneId: TimezoneId): Timezone? = timezoneRepository.findByTimezoneId(timezoneId)
}