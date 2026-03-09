package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.domain.client.WorldServiceClient
import ru.woowy.domain.generation.BirthdayGenerator
import ru.woowy.domain.model.Character
import ru.woowy.domain.model.CharacterRequest
import ru.woowy.domain.repository.CharacterRepository
import ru.woowy.domain.usecase.CharacterUseCase
import ru.woowy.extension.badRequest
import ru.woowy.id.CharacterId
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
        const val LOCATION_DOES_NOT_EXIST = "Location does not exist"
    }

    @Transactional
    override fun create(
        request: CharacterRequest,
        userId: UserId,
    ): Character {
        worldServiceClient
            .getLocation(request.locationId)
            ?: badRequest(LOCATION_DOES_NOT_EXIST)

        val character =
            Character(
                id = UUID.randomUUID(),
                userId = userId,
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
    override fun getAll(userId: UserId): List<Character> = characterRepository.findAllByUser(userId)

    @Transactional
    override fun update(character: Character): Character? = characterRepository.update(character)

    @Transactional
    override fun delete(characterId: CharacterId) = characterRepository.delete(characterId)
}