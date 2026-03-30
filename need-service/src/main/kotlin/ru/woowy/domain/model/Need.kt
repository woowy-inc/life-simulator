package ru.woowy.domain.model

import ru.woowy.model.Percentage
import java.time.OffsetDateTime

data class Need(
    val hunger: Percentage = Percentage.max(),
    val sleep: Percentage = Percentage.max(),
    val body: Percentage = Percentage.max(),
    val mental: Percentage = Percentage.max(),
    val social: Percentage = Percentage.max(),
    val health: Percentage = Percentage.max(),
    val happiness: Percentage = Percentage.max(),
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
)