package ru.woowy.application.usecase.impl

import org.springframework.stereotype.Service
import ru.woowy.application.usecase.TimezoneUseCase
import ru.woowy.domain.model.Timezone
import ru.woowy.domain.model.TimezoneId
import ru.woowy.domain.repository.TimezoneRepository

@Service
internal class TimezoneUseCaseImpl(
    private val timezoneRepository: TimezoneRepository,
) : TimezoneUseCase {
    override fun get(timezoneId: TimezoneId): Timezone? = timezoneRepository.findByTimezoneId(timezoneId)

    override fun add(timezone: Timezone): Timezone = timezoneRepository.add(timezone)

    override fun update(timezone: Timezone): Timezone? = timezoneRepository.update(timezone)

    override fun delete(timezoneId: TimezoneId) = timezoneRepository.delete(timezoneId)
}