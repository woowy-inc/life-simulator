package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.repository.CrudRepository
import ru.woowy.domain.model.TimezoneId
import ru.woowy.infrastructure.persistence.entity.TimezoneEntity

internal interface CrudTimezoneRepository : CrudRepository<TimezoneEntity, TimezoneId>