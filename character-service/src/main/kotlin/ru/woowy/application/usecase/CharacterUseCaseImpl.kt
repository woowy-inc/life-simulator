package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.domain.client.WorldServiceClient
import ru.woowy.domain.generation.BirthdayGenerator
import ru.woowy.domain.model.Character
import ru.woowy.domain.model.CharacterRequest
import ru.woowy.domain.repository.CharacterRepository
import ru.woowy.domain.usecase.CharacterUseCase
import ru.woowy.extension.forbidden
import ru.woowy.extension.notFound
import ru.woowy.id.CharacterId
import ru.woowy.id.LocationId
import ru.woowy.id.UserId
import java.time.LocalDateTime
import java.util.UUID

@Service
class CharacterUseCaseImpl(
    private val characterRepository: CharacterRepository,
    private val birthdayGenerator: BirthdayGenerator,
    private val worldServiceClient: WorldServiceClient,
) : CharacterUseCase {
    companion object {
        const val LOCATION_NOT_FOUND = "Location not found"
        const val CHARACTER_NOT_FOUND = "Character not found"
        const val CHARACTER_NOT_OWNER = "Character not owner"
    }

    @Transactional
    override fun create(
        request: CharacterRequest,
        owner: UserId,
    ): Character {
        verifyLocation(request.locationId)

        val character =
            Character(
                id = UUID.randomUUID(),
                userId = owner,
                name = request.name,
                gender = request.gender,
                birthday = birthdayGenerator.generate(),
                locationId = request.locationId,
                worldId = null,
                createdAt = LocalDateTime.now(),
            )

        return characterRepository.add(character)
    }

    @Transactional(readOnly = true)
    override fun get(characterId: CharacterId): Character? = characterRepository.findByCharacter(characterId)

    @Transactional(readOnly = true)
    override fun getAll(owner: UserId): List<Character> = characterRepository.findAllByUser(owner)

    @Transactional
    override fun update(
        characterId: CharacterId,
        request: CharacterRequest,
        owner: UserId,
    ): Character? {
        val found = get(characterId) ?: notFound(CHARACTER_NOT_FOUND)

        verifyOwner(owner, found)
        verifyLocation(request.locationId)

        val character = found.copy(name = request.name, gender = request.gender, locationId = request.locationId)

        return characterRepository.update(character)
    }

    @Transactional
    override fun delete(
        characterId: CharacterId,
        owner: UserId,
    ) {
        get(characterId)
            ?.let { character -> verifyOwner(owner, character) }
            ?: notFound(CHARACTER_NOT_FOUND)

        characterRepository.delete(characterId)
    }

    private fun verifyOwner(
        owner: UserId,
        character: Character,
    ) {
        if (character.userId != owner) {
            forbidden(CHARACTER_NOT_OWNER)
        }
    }

    private fun verifyLocation(locationId: LocationId) {
        worldServiceClient
            .getLocation(locationId)
            ?: notFound(LOCATION_NOT_FOUND)
    }
}