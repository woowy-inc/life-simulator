package ru.woowy.application.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.domain.messaging.EventPublisher
import ru.woowy.domain.model.World
import ru.woowy.domain.model.WorldRequest
import ru.woowy.domain.repository.WorldRepository
import ru.woowy.domain.usecase.WorldUseCase
import ru.woowy.id.CharacterId
import ru.woowy.id.WorldId
import ru.woowy.infrastructure.mapper.asCreatedEvent
import java.time.LocalDateTime
import java.util.UUID

@Service
class WorldUseCaseImpl(
    private val worldRepository: WorldRepository,
    private val eventPublisher: EventPublisher,
) : WorldUseCase {
    override fun get(worldId: WorldId): World? = worldRepository.findById(worldId)

    override fun getByCharacter(characterId: CharacterId): World? = worldRepository.findByCharacter(characterId)

    @Transactional
    override fun add(request: WorldRequest): World {
        val world =
            World(
                id = UUID.randomUUID(),
                characterId = request.characterId,
                createdAt = LocalDateTime.now(),
            )

        val created = worldRepository.add(world)
        eventPublisher.publish(created.asCreatedEvent())

        return created
    }

    @Transactional
    override fun delete(worldId: WorldId) = worldRepository.delete(worldId)

    @Transactional
    override fun deleteByCharacter(characterId: CharacterId) = worldRepository.deleteByCharacter(characterId)
}