package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import ru.woowy.domain.model.TimezoneId

@Entity(name = "timezones")
internal class TimezoneEntity(
    @Id
    @Column(name = "timezone_id")
    var timezoneId: TimezoneId,
    @Column(length = 20, nullable = false)
    var abbreviation: String,
    @Column(name = "utc_offset", length = 20, nullable = false)
    var utcOffset: String,
    @Column(name = "msk_offset", length = 20, nullable = false)
    var mskOffset: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TimezoneEntity) return false

        return timezoneId == other.timezoneId
    }

    override fun hashCode(): Int = timezoneId.hashCode()
}