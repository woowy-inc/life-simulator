package ru.woowy.domain.model

import ru.woowy.id.CharacterId
import java.time.LocalDateTime

data class GameSessionRequest(
    val characterId: CharacterId,
)