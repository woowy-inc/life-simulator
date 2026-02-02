package ru.woowy.presentation.web.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.service.TimeService
import ru.woowy.game.GameConfig
import java.util.UUID

private const val BASE_ENDPOINT = "/time"

@RestController
@RequestMapping(BASE_ENDPOINT)
internal class TimeController(
    private val timeService: TimeService,
) {
    @PostMapping("/start/{worldId}")
    fun start(
        @PathVariable worldId: UUID,
        @RequestBody config: GameConfig = GameConfig(),
    ) = timeService.startTime(worldId, config)

    @PostMapping("/stop/{worldId}")
    fun stop(
        @PathVariable worldId: UUID,
    ) = timeService.stopTime(worldId)
}