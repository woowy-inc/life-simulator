package ru.woowy.character.domain.client

import ru.woowy.character.domain.model.NeedPreview
import ru.woowy.id.CharacterId

interface NeedServiceClient {
    fun getNeed(characterId: CharacterId): NeedPreview?

    fun getNeeds(characterIds: Array<CharacterId>): Map<CharacterId, NeedPreview>
}