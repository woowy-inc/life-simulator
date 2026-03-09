package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import ru.woowy.domain.model.Gender
import ru.woowy.id.CharacterId
import ru.woowy.id.LocationId
import ru.woowy.id.UserId
import ru.woowy.id.WorldId
import java.time.LocalDateTime

@Entity(name = "characters")
class CharacterEntity(
    @Id
    val id: CharacterId,
    @Column(name = "user_id", nullable = false)
    val userId: UserId,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    @Column(nullable = false)
    val birthday: LocalDateTime,
    @Column(name = "location_id", nullable = false)
    val locationId: LocationId,
    @Column(name = "world_id", nullable = true)
    val worldId: WorldId?,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CharacterEntity) return false

        return this.id == other.id
    }

    override fun hashCode(): Int = this.id.hashCode()
}