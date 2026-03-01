package ru.woowy.location.domain.repository

import ru.woowy.location.domain.model.Timezone
import ru.woowy.location.domain.model.TimezoneId

internal interface TimezoneRepository {
    fun findByTimezoneId(timezoneId: TimezoneId): Timezone?

    fun add(timezone: Timezone): Timezone

    fun update(timezone: Timezone): Timezone?

    fun delete(timezoneId: TimezoneId)
}