package ru.woowy.character.domain.model

data class Need(
    val hunger: Double,
    val sleep: Double,
    val body: Double,
    val mental: Double,
    val social: Double,
    val health: Double,
    val happiness: Double,
)