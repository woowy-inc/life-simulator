package ru.woowy.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.Timezone
import ru.woowy.domain.model.TimezoneId
import ru.woowy.domain.repository.TimezoneRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.persistence.entity.TimezoneEntity
import ru.woowy.infrastructure.persistence.jpa.TimezoneJpaRepository
import kotlin.jvm.optionals.getOrNull

@Repository
class TimezoneRepositoryImpl(
    private val timezoneJpaRepository: TimezoneJpaRepository,
) : TimezoneRepository {
    override fun findByTimezoneId(timezoneId: TimezoneId): Timezone? =
        timezoneJpaRepository.findById(timezoneId).getOrNull()?.asDomain()

    override fun add(timezone: Timezone): Timezone = timezoneJpaRepository.save(timezone.asEntity()).asDomain()

    override fun addOrUpdate(timezones: List<Timezone>): List<Timezone> = timezoneJpaRepository
        .saveAll(timezones.map { it.asEntity() })
        .map { it.asDomain() }

    override fun update(timezone: Timezone): Timezone? = timezoneJpaRepository.save(timezone.asEntity()).asDomain()

    override fun delete(timezoneId: TimezoneId) = timezoneJpaRepository.deleteById(timezoneId)

    private fun Timezone.asEntity() = TimezoneEntity(
        timezoneId = this.timezoneId,
        abbreviation = this.abbreviation,
        utcOffset = this.utcOffset,
        mskOffset = this.mskOffset,
    )
}