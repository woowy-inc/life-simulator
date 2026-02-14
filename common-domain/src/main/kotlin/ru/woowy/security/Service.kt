package ru.woowy.security

enum class Service(
    val id: String,
) {
    GATEWAY_SERVICE("gateway-service"),
    AUTH_SERVICE("auth-service"),
    TIME_SERVICE("time-service"),
    CHARACTER_SERVICE("character-service"),
    WORLD_SERVICE("world-service"),
}