package ru.woowy.domain.repository

import ru.woowy.domain.model.Timezone
import ru.woowy.domain.model.TimezoneId

internal interface TimezoneRepository {
    fun findByTimezoneId(timezoneId: TimezoneId): Timezone?

    fun add(timezone: Timezone): Timezone

    fun update(timezone: Timezone): Timezone?

    fun delete(timezoneId: TimezoneId)
}