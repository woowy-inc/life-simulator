package ru.woowy.infrastructure.buffer

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import ru.woowy.domain.buffer.StateHolder
import ru.woowy.domain.buffer.ThresholdAggregator
import ru.woowy.domain.model.CharacterState
import ru.woowy.domain.model.CharacterStateBuilder
import ru.woowy.domain.model.CharacterStateEvent
import ru.woowy.domain.model.NeedUpdatedEvent
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.lifecycle.ServiceScope
import kotlin.time.Duration.Companion.milliseconds

@Component
class ThresholdAggregatorImpl(
    private val holder: StateHolder<CharacterId, CharacterStateBuilder>,
    private val scope: ServiceScope,
) : ThresholdAggregator {
    companion object {
        private const val THRESHOLD_IN_MS = 300
    }

    override suspend fun threshold(
        characterId: CharacterId,
        event: CharacterStateEvent,
        onRelease: (CharacterState) -> Unit,
    ) {
        val isNew = holder.putIfAbsent(characterId, CharacterStateBuilder()) == null
        val builder = holder.get(characterId) ?: return

        when (event) {
            is NeedUpdatedEvent -> builder.need(event)
        }

        if (isNew) {
            scope.launch {
                delay(THRESHOLD_IN_MS.milliseconds)
                val builder = holder.remove(characterId) ?: return@launch
                onRelease(builder.build())
            }
        }
    }
}