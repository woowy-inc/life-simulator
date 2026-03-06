package ru.woowy.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

typealias TimezoneId = String

data class Timezone(
    @field:JsonProperty("tzid")
    val timezoneId: String,
    val abbreviation: String,
    @field:JsonProperty("utcOffset")
    val utcOffset: String,
    @field:JsonProperty("mskOffset")
    val mskOffset: String,
)