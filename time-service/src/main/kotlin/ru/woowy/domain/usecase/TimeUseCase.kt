package ru.woowy.domain.usecase

import ru.woowy.game.GameConfig
import java.util.UUID

internal interface TimeUseCase {
    fun startTime(
        worldId: UUID,
        config: GameConfig,
    )

    fun pauseTime(worldId: UUID)

    fun resumeTime(worldId: UUID)

    fun stopTime(worldId: UUID)
}