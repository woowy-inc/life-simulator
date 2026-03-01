package ru.woowy.location.infrastructure.persistence.jpa

import org.springframework.data.repository.CrudRepository
import ru.woowy.location.infrastructure.persistence.entity.TimezoneEntity

interface CrudTimezoneRepository : CrudRepository<TimezoneEntity, String>