package ru.woowy.application.usecase

import ru.woowy.game.GameConfig
import java.util.UUID

internal interface WorldUseCase {
    fun startWorld(
        gameId: UUID,
        config: GameConfig,
    )

    fun pauseWorld(gameId: UUID)

    fun stopWorld(gameId: UUID)
}