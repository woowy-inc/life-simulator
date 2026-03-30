package ru.woowy.character.application.usecase

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.assertThrows
import ru.woowy.character.domain.repository.CharacterGameSettingsRepository
import ru.woowy.character.domain.repository.CharacterRepository
import ru.woowy.exception.ForbiddenException
import ru.woowy.exception.NotFoundException
import ru.woowy.helper.randomCharacter
import ru.woowy.helper.randomCharacterGameSettings
import ru.woowy.util.randomUUID

class CharacterGameSettingsUseCaseTest {
    private val characterGameSettingsRepository = mockk<CharacterGameSettingsRepository>()
    private val characterRepository = mockk<CharacterRepository>()

    private val useCase =
        CharacterGameSettingsUseCaseImpl(
            characterGameSettingsRepository = characterGameSettingsRepository,
            characterRepository = characterRepository,
        )

    private val ownerId = randomUUID()
    private val character = randomCharacter(userId = ownerId)
    private val settings = randomCharacterGameSettings(characterId = character.id)

    @Test
    fun `get - success`() {
        every { characterRepository.findById(character.id) } returns character
        every { characterGameSettingsRepository.findById(character.id) } returns settings

        val actual = useCase.get(character.id, ownerId)

        assertEquals(settings, actual)
    }

    @Test
    fun `get - returns null when settings not found`() {
        every { characterRepository.findById(character.id) } returns character
        every { characterGameSettingsRepository.findById(character.id) } returns null

        assertNull(useCase.get(character.id, ownerId))
    }

    @Test
    fun `get - character not found - throws`() {
        every { characterRepository.findById(character.id) } returns null

        assertThrows<NotFoundException> { useCase.get(character.id, ownerId) }
    }

    @Test
    fun `get - not owner - throws`() {
        every { characterRepository.findById(character.id) } returns character

        assertThrows<ForbiddenException> { useCase.get(character.id, randomUUID()) }
    }

    @Test
    fun `addOrUpdate - success`() {
        every { characterRepository.findById(character.id) } returns character
        every { characterGameSettingsRepository.addOrUpdate(settings) } returns settings

        val actual = useCase.addOrUpdate(settings, ownerId)

        assertEquals(settings, actual)
    }

    @Test
    fun `addOrUpdate - character not found - throws`() {
        every { characterRepository.findById(character.id) } returns null

        assertThrows<NotFoundException> { useCase.addOrUpdate(settings, ownerId) }
    }

    @Test
    fun `addOrUpdate - not owner - throws`() {
        every { characterRepository.findById(character.id) } returns character

        assertThrows<ForbiddenException> { useCase.addOrUpdate(settings, randomUUID()) }
    }

    @Test
    fun `delete - success`() {
        every { characterRepository.findById(character.id) } returns character
        every { characterGameSettingsRepository.delete(character.id) } just Runs

        useCase.delete(character.id, ownerId)

        verify { characterGameSettingsRepository.delete(character.id) }
    }

    @Test
    fun `delete - character not found - throws`() {
        every { characterRepository.findById(character.id) } returns null

        assertThrows<NotFoundException> { useCase.delete(character.id, ownerId) }
    }

    @Test
    fun `delete - not owner - throws`() {
        every { characterRepository.findById(character.id) } returns character

        assertThrows<ForbiddenException> { useCase.delete(character.id, randomUUID()) }
        verify(exactly = 0) { characterGameSettingsRepository.delete(any()) }
    }
}