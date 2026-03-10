package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import ru.woowy.id.CharacterId
import ru.woowy.id.WorldId
import java.time.LocalDateTime

@Entity(name = "worlds")
class WorldEntity(
    @Id
    val id: WorldId,
    @Column(name = "character_id", nullable = false)
    val characterId: CharacterId,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WorldEntity) return false

        return this.id == other.id
    }

    override fun hashCode(): Int = this.id.hashCode()
}