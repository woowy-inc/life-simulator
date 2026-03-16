package ru.woowy.application.usecase

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.assertThrows
import ru.woowy.domain.client.CharacterServiceClient
import ru.woowy.domain.model.GameStatus
import ru.woowy.domain.repository.GameSessionRepository
import ru.woowy.domain.session.SessionEngine
import ru.woowy.exception.NotFoundException
import ru.woowy.helper.randomCharacter
import ru.woowy.helper.randomGameSession
import ru.woowy.helper.randomGameSessionRequest
import ru.woowy.util.randomUUID
import java.time.LocalDateTime
import kotlin.test.assertEquals

class GameSessionUseCaseTest {
    private val gameSessionRepository = mockk<GameSessionRepository>()
    private val characterServiceClient = mockk<CharacterServiceClient>()
    private val sessionEngine = mockk<SessionEngine>(relaxed = true)
    private val useCase = GameSessionUseCaseImpl(gameSessionRepository, characterServiceClient, sessionEngine)

    private val request = randomGameSessionRequest()
    private val gameSession = randomGameSession(request.characterId)

    @Test
    fun `should get session by character id`() {
        every { gameSessionRepository.findById(any()) } returns gameSession

        val actual = useCase.get(request.characterId)
        assertEquals(gameSession, actual)

        verify(exactly = 1) { gameSessionRepository.findById(any()) }
    }

    @Test
    fun `should return null when session not found`() {
        every { gameSessionRepository.findById(any()) } returns null

        val actual = useCase.get(request.characterId)
        assertNull(actual)

        verify(exactly = 1) { gameSessionRepository.findById(any()) }
    }

    @Test
    fun `should create session`() {
        val character = randomCharacter(id = request.characterId)
        val expected = randomGameSession(request.characterId)

        every { characterServiceClient.getCharacter(request.characterId) } returns character
        every { gameSessionRepository.add(any()) } returns expected

        val actual = useCase.create(request, randomUUID())
        assertEquals(expected, actual)

        verify(exactly = 1) { characterServiceClient.getCharacter(request.characterId) }
        verify(exactly = 1) { gameSessionRepository.add(any()) }
    }

    @Test
    fun `should throw when character not found on create`() {
        every { characterServiceClient.getCharacter(any()) } returns null

        assertThrows<NotFoundException> { useCase.create(request, randomUUID()) }

        verify(exactly = 0) { gameSessionRepository.add(any()) }
    }

    @Test
    fun `should start existing session`() = runBlocking {
        val inactive = randomGameSession(request.characterId, status = GameStatus.INACTIVE)
        val expected = inactive.copy(status = GameStatus.ACTIVE, pausedAt = null)

        every { gameSessionRepository.findById(request.characterId) } returns inactive
        every { gameSessionRepository.update(any()) } returns expected
        coEvery { sessionEngine.startSimulation(any(), any()) } returns true

        val actual = useCase.start(request.characterId, randomUUID())
        assertEquals(GameStatus.ACTIVE, actual.status)
        assertNull(actual.pausedAt)

        verify(exactly = 1) {
            gameSessionRepository.update(match { it.status == GameStatus.ACTIVE && it.pausedAt == null })
        }
        coVerify(exactly = 1) { sessionEngine.startSimulation(request.characterId, any()) }
    }

    @Test
    fun `should create and start session when not found`() = runBlocking {
        val character = randomCharacter(id = request.characterId)
        val created = randomGameSession(request.characterId, status = GameStatus.INACTIVE)
        val active = created.copy(status = GameStatus.ACTIVE, pausedAt = null)

        every { gameSessionRepository.findById(request.characterId) } returns null
        every { characterServiceClient.getCharacter(request.characterId) } returns character
        every { gameSessionRepository.add(any()) } returns created
        every { gameSessionRepository.update(any()) } returns active
        coEvery { sessionEngine.startSimulation(any(), any()) } returns true

        val actual = useCase.start(request.characterId, randomUUID())
        assertEquals(GameStatus.ACTIVE, actual.status)

        verify(exactly = 1) { gameSessionRepository.add(any()) }
        coVerify(exactly = 1) { sessionEngine.startSimulation(request.characterId, any()) }
    }

    @Test
    fun `should stop session`() = runBlocking {
        val active = randomGameSession(request.characterId, status = GameStatus.ACTIVE, pausedAt = null)
        val expected = active.copy(status = GameStatus.INACTIVE, pausedAt = LocalDateTime.now())

        every { gameSessionRepository.findById(request.characterId) } returns active
        every { gameSessionRepository.update(any()) } returns expected
        coEvery { sessionEngine.stopSimulation(any()) } returns true

        val actual = useCase.stop(request.characterId)
        assertEquals(GameStatus.INACTIVE, actual?.status)
        assertNotNull(actual?.pausedAt)

        coVerify(exactly = 1) { sessionEngine.stopSimulation(request.characterId) }
        verify(exactly = 1) {
            gameSessionRepository.update(
                match {
                    it.status == GameStatus.INACTIVE &&
                        it.pausedAt != null
                },
            )
        }
    }

    @Test
    fun `should throw when session not found on stop`() = runBlocking {
        every { gameSessionRepository.findById(any()) } returns null
        coEvery { sessionEngine.stopSimulation(any()) } returns true

        assertThrows<NotFoundException> { useCase.stop(request.characterId) }

        verify(exactly = 0) { gameSessionRepository.update(any()) }
    }

    @Test
    fun `should delete session`() {
        every { gameSessionRepository.delete(request.characterId) } just Runs

        useCase.delete(request.characterId)

        verify(exactly = 1) { gameSessionRepository.delete(request.characterId) }
    }
}