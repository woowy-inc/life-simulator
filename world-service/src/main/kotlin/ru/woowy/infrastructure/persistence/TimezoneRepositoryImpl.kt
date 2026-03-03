package ru.woowy.infrastructure.persistence

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.Timezone
import ru.woowy.domain.model.TimezoneId
import ru.woowy.domain.repository.TimezoneRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.persistence.entity.TimezoneEntity
import ru.woowy.infrastructure.persistence.jpa.JpaTimezoneRepository
import kotlin.jvm.optionals.getOrNull

@Repository
internal class TimezoneRepositoryImpl(
    private val jpaTimezoneRepository: JpaTimezoneRepository,
) : TimezoneRepository {
    override fun findByTimezoneId(timezoneId: TimezoneId): Timezone? =
        jpaTimezoneRepository.findById(timezoneId).getOrNull()?.asDomain()

    override fun add(timezone: Timezone): Timezone = jpaTimezoneRepository.save(timezone.asEntity()).asDomain()

    override fun update(timezone: Timezone): Timezone? = jpaTimezoneRepository.save(timezone.asEntity()).asDomain()

    override fun delete(timezoneId: TimezoneId) = jpaTimezoneRepository.deleteById(timezoneId)

    internal fun Timezone.asEntity() = TimezoneEntity(
        timezoneId = this.timezoneId,
        abbreviation = this.abbreviation,
        utcOffset = this.utcOffset,
        mskOffset = this.mskOffset,
    )
}