package ru.woowy.presentation.web.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.service.WorldService
import ru.woowy.game.GameConfig
import ru.woowy.presentation.web.endpoint.WorldEndpoint
import java.util.UUID

@RestController
internal class WorldController(
    private val worldService: WorldService,
) {
    @PostMapping(WorldEndpoint.POST_WORLD_START)
    fun startWorld(
        @PathVariable("gameId") gameId: UUID,
        @RequestBody config: GameConfig,
    ) = worldService.startWorld(gameId, config)

    @PostMapping(WorldEndpoint.POST_WORLD_PAUSE)
    fun pause(gameId: UUID) = worldService.pauseWorld(gameId)

    @PostMapping(WorldEndpoint.POST_WORLD_STOP)
    fun finish(gameId: UUID) = worldService.stopWorld(gameId)
}