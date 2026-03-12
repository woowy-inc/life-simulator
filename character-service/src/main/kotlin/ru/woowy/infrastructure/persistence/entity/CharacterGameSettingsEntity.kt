package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import ru.woowy.game.GameSpeed
import ru.woowy.id.CharacterId

@Entity(name = "character_game_settings")
class CharacterGameSettingsEntity(
    @Id
    @Column(name = "character_id")
    var characterId: CharacterId,
    @Enumerated(EnumType.STRING)
    var speed: GameSpeed,
)