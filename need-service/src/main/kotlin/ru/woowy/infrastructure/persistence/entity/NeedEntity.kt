package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID
import ru.woowy.id.CharacterId
import ru.woowy.id.NeedId

@Entity
@Table(name = "needs")
class NeedEntity(
    @Id
    var id: NeedId = UUID.randomUUID(),
    @Column(name = "character_id", nullable = false)
    var characterId: CharacterId,
    @Column(nullable = false, precision = 5, scale = 2)
    var hunger: BigDecimal,
    @Column(nullable = false, precision = 5, scale = 2)
    var sleep: BigDecimal,
    @Column(nullable = false, precision = 5, scale = 2)
    var body: BigDecimal,
    @Column(nullable = false, precision = 5, scale = 2)
    var mental: BigDecimal,
    @Column(nullable = false, precision = 5, scale = 2)
    var social: BigDecimal,
    @Column(nullable = false, precision = 5, scale = 2)
    var health: BigDecimal,
    @Column(nullable = false, precision = 5, scale = 2)
    var happiness: BigDecimal,
    @Column(name = "created_at", nullable = false)
    var createdAt: OffsetDateTime,
)