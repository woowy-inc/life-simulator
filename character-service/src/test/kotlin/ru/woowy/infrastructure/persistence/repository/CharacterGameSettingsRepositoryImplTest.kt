package ru.woowy.infrastructure.persistence.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.game.GameSpeed
import ru.woowy.helper.randomCharacter
import ru.woowy.helper.randomCharacterGameSettings
import ru.woowy.infrastructure.persistence.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.CharacterGameSettingsJpaRepository
import ru.woowy.infrastructure.persistence.jpa.CharacterJpaRepository
import ru.woowy.util.randomUUID

class CharacterGameSettingsRepositoryImplTest
    @Autowired
    constructor(
        private val characterJpaRepository: CharacterJpaRepository,
        private val characterGameSettingsJpaRepository: CharacterGameSettingsJpaRepository,
    ) : JpaRepositoryTest() {
        private val characterRepository = CharacterRepositoryImpl(characterJpaRepository)
        private val repository = CharacterGameSettingsRepositoryImpl(characterGameSettingsJpaRepository)

        @BeforeEach
        fun setUp() {
            characterGameSettingsJpaRepository.deleteAll()
            characterJpaRepository.deleteAll()
        }

        @Test
        fun `settings should be added`() {
            val character = characterRepository.add(randomCharacter())
            val settings = randomCharacterGameSettings(characterId = character.id)

            val actual = repository.addOrUpdate(settings)

            assertEquals(settings, actual)
        }

        @Test
        fun `settings should be found by character id`() {
            val character = characterRepository.add(randomCharacter())
            val settings = repository.addOrUpdate(randomCharacterGameSettings(characterId = character.id))

            val actual = repository.findById(character.id)

            assertEquals(settings, actual)
        }

        @Test
        fun `settings should not be found for unknown character`() {
            val actual = repository.findById(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `settings should be updated`() {
            val character = characterRepository.add(randomCharacter())
            val saved = repository.addOrUpdate(randomCharacterGameSettings(characterId = character.id))
            val updated = saved.copy(speed = GameSpeed.FAST)

            val actual = repository.addOrUpdate(updated)

            assertEquals(updated, actual)
        }

        @Test
        fun `settings should be deleted`() {
            val character = characterRepository.add(randomCharacter())
            val saved = repository.addOrUpdate(randomCharacterGameSettings(characterId = character.id))

            repository.delete(saved.characterId)

            assertNull(repository.findById(saved.characterId))
        }
    }