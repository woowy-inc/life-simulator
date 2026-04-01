package ru.woowy.character.domain.client

import ru.woowy.character.domain.model.Need
import ru.woowy.id.CharacterId

interface NeedServiceClient {
    fun getNeeds(characterIds: Array<CharacterId>): Map<CharacterId, Need>
}