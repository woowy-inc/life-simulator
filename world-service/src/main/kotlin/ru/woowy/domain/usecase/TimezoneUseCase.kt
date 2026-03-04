package ru.woowy.domain.usecase

import ru.woowy.domain.model.Timezone
import ru.woowy.domain.model.TimezoneId

interface TimezoneUseCase {
    fun get(timezoneId: TimezoneId): Timezone?

    fun add(timezone: Timezone): Timezone

    fun update(timezone: Timezone): Timezone?

    fun delete(timezoneId: TimezoneId)
}