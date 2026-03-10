package ru.woowy.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import ru.woowy.domain.messaging.EventPublisher
import ru.woowy.domain.repository.WorldRepository
import ru.woowy.helper.randomWorld
import ru.woowy.helper.randomWorldRequest
import ru.woowy.util.randomUUID
import kotlin.test.assertEquals

class WorldUseCaseTest {
    private val worldRepository = mockk<WorldRepository>(relaxed = true)
    private val eventPublisher = mockk<EventPublisher>(relaxed = true)
    private val useCase = WorldUseCaseImpl(worldRepository, eventPublisher)

    @Test
    fun `world should be found by world id`() {
        val world = randomWorld()
        every { worldRepository.findById(world.id) } returns world

        val actual = useCase.get(world.id)

        assertEquals(world, actual)
        verify(exactly = 1) { worldRepository.findById(world.id) }
    }

    @Test
    fun `world should not be found by world id`() {
        val worldId = randomUUID()
        every { worldRepository.findById(worldId) } returns null

        val actual = useCase.get(worldId)

        assertNull(actual)
        verify(exactly = 1) { worldRepository.findById(worldId) }
    }

    @Test
    fun `world should be found by character id`() {
        val world = randomWorld()
        every { worldRepository.findByCharacter(world.characterId) } returns world

        val actual = useCase.getByCharacter(world.characterId)

        assertEquals(world, actual)
        verify(exactly = 1) { worldRepository.findByCharacter(world.characterId) }
    }

    @Test
    fun `world should not be found by character id`() {
        val characterId = randomUUID()
        every { worldRepository.findByCharacter(characterId) } returns null

        val actual = useCase.getByCharacter(characterId)

        assertNull(actual)
        verify(exactly = 1) { worldRepository.findByCharacter(characterId) }
    }

    @Test
    fun `world should be added`() {
        val world = randomWorld()
        val request = randomWorldRequest(characterId = world.characterId)
        every { worldRepository.add(any()) } returns world

        val actual = useCase.add(request)

        assertEquals(world, actual)
        verify(exactly = 1) { worldRepository.add(any()) }
        verify(exactly = 1) { eventPublisher.publish(any()) }
    }

    @Test
    fun `world should be deleted`() {
        val worldId = randomUUID()

        useCase.delete(worldId)

        verify(exactly = 1) { worldRepository.delete(worldId) }
    }

    @Test
    fun `world should be deleted by character id`() {
        val characterId = randomUUID()

        useCase.deleteByCharacter(characterId)

        verify(exactly = 1) { worldRepository.deleteByCharacter(characterId) }
    }
}