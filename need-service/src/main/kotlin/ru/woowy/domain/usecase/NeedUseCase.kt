package ru.woowy.domain.usecase

import ru.woowy.domain.model.Need
import ru.woowy.game.GameSpeed
import ru.woowy.id.CharacterId

interface NeedUseCase {
    suspend fun getNeed(characterId: CharacterId): Need

    suspend fun processTick(
        characterId: CharacterId,
        tickNumber: Long,
        gameSpeed: GameSpeed,
    )
}