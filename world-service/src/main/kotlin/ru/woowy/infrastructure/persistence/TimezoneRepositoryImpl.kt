package ru.woowy.infrastructure.persistence

import org.springframework.stereotype.Repository
import ru.woowy.domain.model.Timezone
import ru.woowy.domain.model.TimezoneId
import ru.woowy.domain.repository.TimezoneRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.mapper.asEntity
import ru.woowy.infrastructure.persistence.jpa.CrudTimezoneRepository
import kotlin.jvm.optionals.getOrNull

@Repository
internal class TimezoneRepositoryImpl(
    private val crudRepository: CrudTimezoneRepository,
) : TimezoneRepository {
    override fun findByTimezoneId(timezoneId: TimezoneId): Timezone? =
        crudRepository.findById(timezoneId).getOrNull()?.asDomain()

    override fun add(timezone: Timezone): Timezone = crudRepository.save(timezone.asEntity()).asDomain()

    override fun update(timezone: Timezone): Timezone? = crudRepository.save(timezone.asEntity()).asDomain()

    override fun delete(timezoneId: TimezoneId) = crudRepository.deleteById(timezoneId)
}