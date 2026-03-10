package ru.woowy.infrastructure.persistence.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.helper.randomWorld
import ru.woowy.infrastructure.persistence.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.WorldJpaRepository
import ru.woowy.util.randomUUID

class WorldRepositoryImplTest
    @Autowired
    constructor(
        private val worldJpaRepository: WorldJpaRepository,
    ) : JpaRepositoryTest() {
        private val repository = WorldRepositoryImpl(worldJpaRepository)

        @BeforeEach
        fun setUp() = worldJpaRepository.deleteAll()

        @Test
        fun `world should be added`() {
            val world = randomWorld()

            val actual = repository.add(world)

            assertEquals(world, actual)
        }

        @Test
        fun `world should be found by world id`() {
            val saved = repository.add(randomWorld())

            val actual = repository.findById(saved.id)

            assertEquals(saved, actual)
        }

        @Test
        fun `world should not be found by world id`() {
            val actual = repository.findById(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `world should be found by character id`() {
            val saved = repository.add(randomWorld())

            val actual = repository.findByCharacter(saved.characterId)

            assertEquals(saved, actual)
        }

        @Test
        fun `world should not be found by character id`() {
            val actual = repository.findByCharacter(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `world should be deleted`() {
            val saved = repository.add(randomWorld())

            repository.delete(saved.id)

            assertNull(repository.findById(saved.id))
        }

        @Test
        fun `world should be deleted by character id`() {
            val saved = repository.add(randomWorld())

            repository.deleteByCharacter(saved.characterId)

            assertNull(repository.findByCharacter(saved.characterId))
        }
    }