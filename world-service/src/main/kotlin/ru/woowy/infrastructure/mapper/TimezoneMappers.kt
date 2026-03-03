package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.Timezone
import ru.woowy.infrastructure.persistence.entity.TimezoneEntity

internal fun TimezoneEntity.asDomain() = Timezone(
    timezoneId = this.timezoneId,
    abbreviation = this.abbreviation,
    utcOffset = this.utcOffset,
    mskOffset = this.mskOffset,
)