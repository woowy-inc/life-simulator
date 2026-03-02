package ru.woowy.helper

import ru.woowy.domain.model.Timezone
import ru.woowy.domain.model.TimezoneId
import ru.woowy.util.randomString

internal fun randomTimezone(
    timezoneId: TimezoneId = randomString(),
    abbreviation: String = randomString(),
    utcOffset: String = randomString(),
    mskOffset: String = randomString(),
): Timezone = Timezone(
    timezoneId = timezoneId,
    abbreviation = abbreviation,
    utcOffset = utcOffset,
    mskOffset = mskOffset,
)