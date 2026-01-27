package ru.woowy.character

import java.time.Instant
import java.util.UUID

data class Human(
    override val id: UUID,
    override val name: String,
    override val birthday: Instant,
    override val createdAt: Instant,
    val gender: Gender,
) : Character()
