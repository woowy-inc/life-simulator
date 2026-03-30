package ru.woowy.character.domain.generation

import java.time.LocalDateTime

interface BirthdayGenerator {
    fun generate(): LocalDateTime
}