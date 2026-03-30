package ru.woowy.infrastructure.persistence.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.domain.model.GameStatus
import ru.woowy.helper.randomGameSession
import ru.woowy.infrastructure.persistence.JpaRepositoryTest
import ru.woowy.infrastructure.persistence.jpa.GameSessionJpaRepository
import ru.woowy.util.randomUUID

class GameSessionRepositoryTest
    @Autowired
    constructor(
        private val gameSessionJpaRepository: GameSessionJpaRepository,
    ) : JpaRepositoryTest() {
        private val repository = GameSessionRepositoryImpl(gameSessionJpaRepository)

        @BeforeEach
        fun setUp() = gameSessionJpaRepository.deleteAll()

        @Test
        fun `session should be added`() {
            val session = randomGameSession()

            val actual = repository.add(session)

            assertEquals(session, actual)
        }

        @Test
        fun `session should be found by character id`() {
            val session = repository.add(randomGameSession())

            val actual = repository.findById(session.characterId)

            assertEquals(session, actual)
        }

        @Test
        fun `session should not be found by character id`() {
            val actual = repository.findById(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `session should be updated`() {
            val saved = repository.add(randomGameSession())
            val updated = saved.copy(status = GameStatus.ACTIVE, pausedAt = null)

            val actual = repository.update(updated)

            assertEquals(updated, actual)
        }

        @Test
        fun `session should be deleted`() {
            val saved = repository.add(randomGameSession())

            repository.delete(saved.characterId)

            assertNull(repository.findById(saved.characterId))
        }
    }