package ru.woowy.location.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import ru.woowy.location.domain.model.TimezoneId

@Entity(name = "timezones")
data class TimezoneEntity(
    @Id
    @Column("timezone_id")
    val timezoneId: TimezoneId,
    @Column(length = 20, nullable = false)
    val abbreviation: String,
    @Column("utc_offset", length = 20, nullable = false)
    val utcOffset: String,
    @Column("msk_offset", length = 20, nullable = false)
    val mskOffset: String,
)