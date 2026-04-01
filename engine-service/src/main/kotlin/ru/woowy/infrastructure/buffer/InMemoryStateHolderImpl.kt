package ru.woowy.infrastructure.buffer

import org.springframework.stereotype.Component
import ru.woowy.domain.buffer.StateHolder
import ru.woowy.domain.model.CharacterStateBuilder
import ru.woowy.id.CharacterId
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryStateHolderImpl : StateHolder<CharacterId, CharacterStateBuilder> {
    private val states = ConcurrentHashMap<CharacterId, CharacterStateBuilder>()

    override suspend fun get(key: CharacterId): CharacterStateBuilder? = states[key]

    override suspend fun put(
        key: CharacterId,
        value: CharacterStateBuilder,
    ) {
        states[key] = value
    }

    override suspend fun putIfAbsent(
        key: CharacterId,
        value: CharacterStateBuilder,
    ): CharacterStateBuilder? = states.putIfAbsent(key, value)

    override suspend fun remove(key: CharacterId): CharacterStateBuilder? = states.remove(key)
}