package ru.woowy.domain.client

import ru.woowy.domain.model.Character
import ru.woowy.domain.model.GameSettings
import ru.woowy.id.CharacterId

interface CharacterServiceClient {
    fun getCharacter(id: CharacterId): Character?

    fun getGameSettings(id: CharacterId): GameSettings?
}