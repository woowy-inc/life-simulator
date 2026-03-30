package ru.woowy.character.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import ru.woowy.account.infrastructure.persistence.entity.AccountEntity
import ru.woowy.character.Gender
import ru.woowy.id.CharacterId
import ru.woowy.id.LocationId
import ru.woowy.id.UserId
import ru.woowy.id.WorldId
import java.time.LocalDateTime

@Entity(name = "characters")
class CharacterEntity(
    @Id
    var id: CharacterId,
    @Column(name = "user_id", nullable = false)
    var userId: UserId,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var gender: Gender,
    @Column(nullable = false)
    var birthday: LocalDateTime,
    @Column(name = "location_id", nullable = false)
    var locationId: LocationId,
    @Column(name = "world_id", nullable = true)
    var worldId: WorldId?,
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime,
//    @OneToMany(mappedBy = "characterId")
//    var accounts: Collection<AccountEntity>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CharacterEntity) return false

        return this.id == other.id
    }

    override fun hashCode(): Int = this.id.hashCode()
}