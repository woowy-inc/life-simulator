package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.domain.model.TimezoneId
import ru.woowy.infrastructure.persistence.entity.TimezoneEntity

interface TimezoneJpaRepository : JpaRepository<TimezoneEntity, TimezoneId>