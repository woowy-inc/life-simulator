package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.persistence.entity.GameSessionEntity

interface GameSessionJpaRepository : JpaRepository<GameSessionEntity, CharacterId>