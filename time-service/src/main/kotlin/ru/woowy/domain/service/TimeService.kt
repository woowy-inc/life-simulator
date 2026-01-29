package ru.woowy.domain.service

import ru.woowy.game.GameConfig
import java.util.UUID

internal interface TimeService {
    fun startTime(
        worldId: UUID,
        config: GameConfig,
    )

    fun stopTime(worldId: UUID)
}