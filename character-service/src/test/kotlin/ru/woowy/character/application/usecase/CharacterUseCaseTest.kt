package ru.woowy.character.application.usecase

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.assertThrows
import ru.woowy.account.domain.usecase.AccountUseCase
import ru.woowy.character.domain.client.WorldServiceClient
import ru.woowy.character.domain.generation.BirthdayGenerator
import ru.woowy.character.domain.repository.CharacterRepository
import ru.woowy.character.infrastructure.lifecycle.ServiceScope
import ru.woowy.domain.messaging.EventPublisher
import ru.woowy.exception.ForbiddenException
import ru.woowy.exception.NotFoundException
import ru.woowy.helper.randomCharacter
import ru.woowy.helper.randomCharacterRequest
import ru.woowy.id.WorldId
import ru.woowy.util.randomUUID

class CharacterUseCaseTest {
    private val characterRepository = mockk<CharacterRepository>()
    private val birthdayGenerator = mockk<BirthdayGenerator>()
    private val worldServiceClient = mockk<WorldServiceClient>()
    private val eventPublisher = mockk<EventPublisher>(relaxed = true)
    private val accountUseCase = mockk<AccountUseCase>(relaxed = true)
    private val serviceScope = ServiceScope(dispatcher = Dispatchers.Unconfined)

    private val useCase =
        CharacterUseCaseImpl(
            characterRepository = characterRepository,
            birthdayGenerator = birthdayGenerator,
            worldServiceClient = worldServiceClient,
            eventPublisher = eventPublisher,
            accountUseCase = accountUseCase,
            serviceScope = serviceScope,
        )

    private val ownerId = randomUUID()
    private val locationId = randomUUID()
    private val characterId = randomUUID()
    private val character = randomCharacter(id = characterId, userId = ownerId, locationId = locationId)
    private val request = randomCharacterRequest(locationId = locationId)

    @Test
    fun `create - success`() {
        every { worldServiceClient.getLocation(locationId) } returns mockk()
        every { birthdayGenerator.generate() } returns character.birthday
        every { characterRepository.add(any()) } returns character
        every { eventPublisher.publish(any()) } just Runs

        val result = useCase.create(request, ownerId)

        assertEquals(character, result)
        verify { eventPublisher.publish(any()) }
    }

    @Test
    fun `create - location not found - throws`() {
        every { worldServiceClient.getLocation(locationId) } returns null

        assertThrows<NotFoundException> { useCase.create(request, ownerId) }
        verify(exactly = 0) { characterRepository.add(any()) }
    }

    @Test
    fun `get - found`() {
        every { characterRepository.findById(characterId) } returns character

        assertEquals(character, useCase.get(characterId))
    }

    @Test
    fun `get - not found - returns null`() {
        every { characterRepository.findById(characterId) } returns null

        assertNull(useCase.get(characterId))
    }

    @Test
    fun `getAll - returns list`() {
        every { characterRepository.findAllByUser(ownerId) } returns listOf(character)

        assertEquals(listOf(character), useCase.getAll(ownerId))
    }

    @Test
    fun `update - success`() {
        every { characterRepository.findById(characterId) } returns character
        every { worldServiceClient.getLocation(locationId) } returns mockk()
        every { characterRepository.update(any()) } returns character

        val result = useCase.update(characterId, request, ownerId)

        assertEquals(character, result)
    }

    @Test
    fun `update - character not found - throws`() {
        every { characterRepository.findById(characterId) } returns null

        assertThrows<NotFoundException> { useCase.update(characterId, request, ownerId) }
    }

    @Test
    fun `update - not owner - throws`() {
        every { characterRepository.findById(characterId) } returns character

        assertThrows<ForbiddenException> { useCase.update(characterId, request, randomUUID()) }
    }

    @Test
    fun `update - location not found - throws`() {
        every { characterRepository.findById(characterId) } returns character
        every { worldServiceClient.getLocation(locationId) } returns null

        assertThrows<NotFoundException> { useCase.update(characterId, request, ownerId) }
    }

    @Test
    fun `update worldId - success`() {
        val worldId: WorldId = randomUUID()
        every { characterRepository.findById(characterId) } returns character
        every { characterRepository.update(any()) } returns character.copy(worldId = worldId)

        val result = useCase.update(characterId, worldId)

        assertEquals(worldId, result?.worldId)
    }

    @Test
    fun `update worldId - character not found - returns null`() {
        every { characterRepository.findById(characterId) } returns null

        assertNull(useCase.update(characterId, randomUUID()))
    }

    @Test
    fun `delete - success`() {
        every { characterRepository.findById(characterId) } returns character
        every { characterRepository.delete(characterId) } just Runs

        useCase.delete(characterId, ownerId)

        verify { characterRepository.delete(characterId) }
        verify { eventPublisher.publish(any()) }
    }

    @Test
    fun `delete - character not found - throws`() {
        every { characterRepository.findById(characterId) } returns null

        assertThrows<NotFoundException> { useCase.delete(characterId, ownerId) }
        verify(exactly = 0) { characterRepository.delete(any()) }
    }

    @Test
    fun `delete - not owner - throws`() {
        every { characterRepository.findById(characterId) } returns character

        assertThrows<ForbiddenException> { useCase.delete(characterId, randomUUID()) }
        verify(exactly = 0) { characterRepository.delete(any()) }
    }
}