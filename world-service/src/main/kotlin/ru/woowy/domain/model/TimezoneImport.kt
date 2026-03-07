package ru.woowy.domain.model

data class TimezoneImport(
    val tzid: String,
    val abbreviation: String,
    val utcOffset: String,
    val mskOffset: String,
)