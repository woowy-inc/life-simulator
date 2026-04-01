package ru.woowy.infrastructure.mapper

import java.util.UUID
import kotlin.time.Clock
import ru.woowy.domain.model.Need
import ru.woowy.domain.model.NeedUpdatedEvent
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.persistence.entity.NeedEntity
import ru.woowy.model.Percentage

fun NeedEntity.asDomain(): Need = Need(
    hunger = Percentage.of(this.hunger),
    sleep = Percentage.of(this.sleep),
    body = Percentage.of(this.body),
    mental = Percentage.of(this.mental),
    social = Percentage.of(this.social),
    health = Percentage.of(this.health),
    happiness = Percentage.of(this.happiness),
    createdAt = this.createdAt,
)

fun Iterable<NeedEntity>.asDomain(): Collection<Need> = this.map { it.asDomain() }

fun Need.asUpdatedEvent(
    characterId: CharacterId,
    tickNumber: Long,
): NeedUpdatedEvent = NeedUpdatedEvent(
    eventId = UUID.randomUUID(),
    timestamp = Clock.System.now().toEpochMilliseconds(),
    characterId = characterId,
    tickNumber = tickNumber,
    hunger = this.hunger.toDouble(),
    sleep = this.sleep.toDouble(),
    body = this.body.toDouble(),
    mental = this.mental.toDouble(),
    social = this.social.toDouble(),
    health = this.health.toDouble(),
    happiness = this.happiness.toDouble(),
)