package ru.woowy.character.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.character.infrastructure.persistence.entity.CharacterGameSettingsEntity
import ru.woowy.id.CharacterId

interface CharacterGameSettingsJpaRepository : JpaRepository<CharacterGameSettingsEntity, CharacterId>