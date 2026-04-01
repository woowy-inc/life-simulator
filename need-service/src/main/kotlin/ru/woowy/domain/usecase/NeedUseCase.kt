package ru.woowy.domain.usecase

import ru.woowy.domain.model.Need
import ru.woowy.game.GameSpeed
import ru.woowy.id.CharacterId

interface NeedUseCase {
    fun getNeed(characterId: CharacterId): Need

    fun processTick(
        characterId: CharacterId,
        tickNumber: Long,
        gameSpeed: GameSpeed,
    )

    fun delete(characterId: CharacterId)
}