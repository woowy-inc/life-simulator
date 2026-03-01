package ru.woowy.location.domain.model

typealias TimezoneId = String

internal data class Timezone(
    val timezoneId: TimezoneId,
    val abbreviation: String,
    val utcOffset: String,
    val mskOffset: String,
)