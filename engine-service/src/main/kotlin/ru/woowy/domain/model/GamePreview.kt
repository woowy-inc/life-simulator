package ru.woowy.domain.model

import java.time.LocalDateTime

data class GamePreview(
    val tickNumber: Long,
    val gameTime: LocalDateTime,
)