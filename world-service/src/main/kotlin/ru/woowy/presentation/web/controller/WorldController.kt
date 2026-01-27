package ru.woowy.presentation.web.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.woowy.application.usecase.WorldUseCase
import ru.woowy.game.GameConfig
import java.util.UUID

private const val BASE_ENDPOINT = "/world"

@RestController
internal class WorldController(
    private val worldUseCase: WorldUseCase,
) {
    @PostMapping("$BASE_ENDPOINT/start/{gameId}")
    fun startWorld(
        @PathVariable gameId: UUID,
        @RequestBody config: GameConfig,
    ) = worldUseCase.startWorld(gameId, config)

    @PostMapping("$BASE_ENDPOINT/pause/{gameId}")
    fun pause(
        @PathVariable gameId: UUID,
    ) = worldUseCase.pauseWorld(gameId)

    @PostMapping("$BASE_ENDPOINT/resume/{gameId}")
    fun resume(
        @PathVariable gameId: UUID,
    ) = worldUseCase.resumeWorld(gameId)

    @PostMapping("$BASE_ENDPOINT/stop/{gameId}")
    fun stop(
        @PathVariable gameId: UUID,
    ) = worldUseCase.stopWorld(gameId)
}