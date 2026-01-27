package ru.woowy.presentation.web.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.usecase.TimeUseCase
import ru.woowy.game.GameConfig
import java.util.UUID

private const val BASE_ENDPOINT = "/time"

@RestController
internal class TimeController(
    private val timeUseCase: TimeUseCase,
) {
    @PostMapping("$BASE_ENDPOINT/start/{worldId}")
    fun start(
        @PathVariable worldId: UUID,
        @RequestBody config: GameConfig,
    ) = timeUseCase.startTime(worldId, config)

    @PostMapping("$BASE_ENDPOINT/pause/{worldId}")
    fun pause(
        @PathVariable worldId: UUID,
    ) = timeUseCase.pauseTime(worldId)

    @PostMapping("$BASE_ENDPOINT/resume/{worldId}")
    fun resume(
        @PathVariable worldId: UUID,
    ) = timeUseCase.resumeTime(worldId)

    @PostMapping("$BASE_ENDPOINT/stop/{worldId}")
    fun stop(
        @PathVariable worldId: UUID,
    ) = timeUseCase.stopTime(worldId)
}