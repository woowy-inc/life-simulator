package ru.woowy.infrastructure.buffer

import java.util.concurrent.ConcurrentHashMap
import org.springframework.stereotype.Component
import ru.woowy.domain.buffer.StateHolder
import ru.woowy.domain.model.CharacterKey
import ru.woowy.domain.model.CharacterStateBuilder

@Component
class InMemoryStateHolder : StateHolder<CharacterKey, CharacterStateBuilder> {
    private val states = ConcurrentHashMap<CharacterKey, CharacterStateBuilder>()

    override suspend fun get(key: CharacterKey): CharacterStateBuilder? = states[key]

    override suspend fun put(
        key: CharacterKey,
        value: CharacterStateBuilder,
    ) {
        states[key] = value
    }

    override suspend fun putIfAbsent(
        key: CharacterKey,
        value: CharacterStateBuilder,
    ): CharacterStateBuilder? = states.putIfAbsent(key, value)

    override suspend fun remove(key: CharacterKey): CharacterStateBuilder? = states.remove(key)
}