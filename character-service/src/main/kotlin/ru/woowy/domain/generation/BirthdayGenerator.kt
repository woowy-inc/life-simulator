package ru.woowy.domain.generation

import java.time.LocalDateTime

interface BirthdayGenerator {
    fun generate(): LocalDateTime
}