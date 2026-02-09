package ru.woowy.character

import java.time.Instant
import java.util.UUID

abstract class Character {
    abstract val id: UUID
    abstract val name: String
    abstract val birthday: Instant
    abstract val createdAt: Instant
}