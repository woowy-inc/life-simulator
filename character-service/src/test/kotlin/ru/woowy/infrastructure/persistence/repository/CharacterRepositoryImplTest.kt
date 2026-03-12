package ru.woowy.infrastructure.persistence.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.domain.model.Character
import ru.woowy.helper.randomCharacter
import ru.woowy.infrastructure.persistence.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.CharacterJpaRepository
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID

class CharacterRepositoryImplTest
    @Autowired
    constructor(
        private val characterJpaRepository: CharacterJpaRepository,
    ) : JpaRepositoryTest() {
        private val repository = CharacterRepositoryImpl(characterJpaRepository)

        @BeforeEach
        fun setUp() = characterJpaRepository.deleteAll()

        @Test
        fun `character should be added`() {
            val character = randomCharacter()

            val actual = repository.add(character)

            assertEquals(character, actual)
        }

        @Test
        fun `character should be found by id`() {
            val character = repository.add(randomCharacter())

            val actual = repository.findById(character.id)

            assertEquals(character, actual)
        }

        @Test
        fun `character should not be found by id`() {
            val actual = repository.findById(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `characters should be found by user`() {
            val userId = randomUUID()
            val first = repository.add(randomCharacter(userId = userId))
            val second = repository.add(randomCharacter(userId = userId))
            repository.add(randomCharacter()) // другой пользователь

            val actual = repository.findAllByUser(userId)

            assertEquals(listOf(first, second), actual)
        }

        @Test
        fun `characters should return empty list for unknown user`() {
            val actual = repository.findAllByUser(randomUUID())

            assertEquals(emptyList<Character>(), actual)
        }

        @Test
        fun `character should be updated`() {
            val saved = repository.add(randomCharacter())
            val updated = saved.copy(name = randomString())

            val actual = repository.update(updated)

            assertEquals(updated, actual)
        }

        @Test
        fun `character should be deleted`() {
            val saved = repository.add(randomCharacter())

            repository.delete(saved.id)

            assertNull(repository.findById(saved.id))
        }
    }