package ru.woowy.character.application.usecase

import java.time.LocalDateTime
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.account.domain.model.AccountType
import ru.woowy.account.domain.usecase.AccountUseCase
import ru.woowy.account.infrastructure.extension.DEFAULT_CURRENCY
import ru.woowy.character.domain.client.NeedServiceClient
import ru.woowy.character.domain.client.WorldServiceClient
import ru.woowy.character.domain.generation.BirthdayGenerator
import ru.woowy.character.domain.model.Character
import ru.woowy.character.domain.model.CharacterRequest
import ru.woowy.character.domain.repository.CharacterRepository
import ru.woowy.character.domain.usecase.CharacterUseCase
import ru.woowy.character.infrastructure.mapper.asCreatedEvent
import ru.woowy.character.infrastructure.mapper.asDeletedEvent
import ru.woowy.domain.messaging.EventPublisher
import ru.woowy.extension.forbidden
import ru.woowy.extension.notFound
import ru.woowy.id.CharacterId
import ru.woowy.id.LocationId
import ru.woowy.id.UserId
import ru.woowy.id.WorldId

@Service
class CharacterUseCaseImpl(
    private val characterRepository: CharacterRepository,
    private val birthdayGenerator: BirthdayGenerator,
    private val worldServiceClient: WorldServiceClient,
    private val needServiceClient: NeedServiceClient,
    private val eventPublisher: EventPublisher,
    private val accountUseCase: AccountUseCase,
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

        val createdCharacter = characterRepository.add(character)

        accountUseCase.addAccount(createdCharacter.id, AccountType.CASH, DEFAULT_CURRENCY)
        eventPublisher.publish(createdCharacter.asCreatedEvent())

        return createdCharacter
    }

    override fun get(characterId: CharacterId): Character? = characterRepository.findById(characterId)

    override fun getAll(owner: UserId): List<Character> {
        val characters = characterRepository.findAllByUser(owner)
        val needs = needServiceClient.getNeeds(characters.map { it.id }.toTypedArray())

        return characters.map { character ->
            character.copy(need = needs[character.id])
        }
    }

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