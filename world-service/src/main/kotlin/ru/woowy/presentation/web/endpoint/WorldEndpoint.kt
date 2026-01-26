package ru.woowy.presentation.web.endpoint

internal object WorldEndpoint {
    const val BASE = "/world"
    const val POST_WORLD_START = "$BASE/start/{gameId}"
    const val POST_WORLD_PAUSE = "$BASE/pause"
    const val POST_WORLD_STOP = "$BASE/stop"
}