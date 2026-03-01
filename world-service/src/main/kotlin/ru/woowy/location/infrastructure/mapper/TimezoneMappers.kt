package ru.woowy.location.infrastructure.mapper

import ru.woowy.location.domain.model.Timezone
import ru.woowy.location.infrastructure.persistence.entity.TimezoneEntity

internal fun TimezoneEntity.asDomain() = Timezone(
    timezoneId = this.timezoneId,
    abbreviation = this.abbreviation,
    utcOffset = this.utcOffset,
    mskOffset = this.mskOffset,
)

internal fun Timezone.asEntity() = TimezoneEntity(
    timezoneId = this.timezoneId,
    abbreviation = this.abbreviation,
    utcOffset = this.utcOffset,
    mskOffset = this.mskOffset,
)