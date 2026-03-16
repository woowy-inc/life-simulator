package ru.woowy.character.infrastructure.generation

import org.springframework.stereotype.Component
import ru.woowy.character.domain.generation.BirthdayGenerator
import java.time.LocalDateTime

@Component
class BirthdayGeneratorImpl : BirthdayGenerator {
    companion object {
        private const val INITIAL_YEARS = 17L
    }

    override fun generate(): LocalDateTime = LocalDateTime.now().minusYears(INITIAL_YEARS)
}