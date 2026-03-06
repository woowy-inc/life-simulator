package ru.woowy.domain.model

typealias TimezoneId = String

data class Timezone(
    val timezoneId: TimezoneId,
    val abbreviation: String,
    val utcOffset: String,
    val mskOffset: String,
)