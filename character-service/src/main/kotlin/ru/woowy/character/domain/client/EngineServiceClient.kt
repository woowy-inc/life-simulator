package ru.woowy.character.domain.client

import ru.woowy.character.domain.model.GamePreview
import ru.woowy.id.CharacterId

interface EngineServiceClient {
    fun getGame(characterId: CharacterId): GamePreview?
}