package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.domain.client.WorldServiceClient
import ru.woowy.domain.generation.BirthdayGenerator
import ru.woowy.domain.model.Character
import ru.woowy.domain.model.CharacterRequest
import ru.woowy.domain.publication.EventPublisher
import ru.woowy.domain.repository.CharacterRepository
import ru.woowy.domain.usecase.CharacterUseCase
import ru.woowy.extension.forbidden
import ru.woowy.extension.notFound
import ru.woowy.id.CharacterId
import ru.woowy.id.LocationId
import ru.woowy.id.UserId
import ru.woowy.id.WorldId
import ru.woowy.infrastructure.mapper.asCreatedEvent
import ru.woowy.infrastructure.mapper.asDeletedEvent
import java.time.LocalDateTime
import java.util.UUID

@Service
class CharacterUseCaseImpl(
    private val characterRepository: CharacterRepository,
    private val birthdayGenerator: BirthdayGenerator,
    private val worldServiceClient: WorldServiceClient,
    private val eventPublisher: EventPublisher,
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

        val created = characterRepository.add(character)
        eventPublisher.publish(created.asCreatedEvent())

        return created
    }

    override fun get(characterId: CharacterId): Character? = characterRepository.findByCharacter(characterId)

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
    override fun update(
        characterId: CharacterId,
        worldId: WorldId,
    ): Character? {
        val character = get(characterId) ?: return null
        return characterRepository.update(character.copy(worldId = worldId))
    }

    @Transactional
    override fun delete(
        characterId: CharacterId,
        owner: UserId,
    ) {
        val character = get(characterId) ?: notFound(CHARACTER_NOT_FOUND)
        verifyOwner(owner, character)

        characterRepository.delete(characterId)
        eventPublisher.publish(character.asDeletedEvent())
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