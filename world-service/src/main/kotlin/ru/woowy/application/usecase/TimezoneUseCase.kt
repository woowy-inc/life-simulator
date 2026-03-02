package ru.woowy.application.usecase

import ru.woowy.domain.model.Timezone
import ru.woowy.domain.model.TimezoneId

internal interface TimezoneUseCase {
    fun get(timezoneId: TimezoneId): Timezone?

    fun add(timezone: Timezone): Timezone

    fun update(timezone: Timezone): Timezone?

    fun delete(timezoneId: TimezoneId)
}