package ru.woowy.infrastructure.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.model.GameSessionDto
import ru.woowy.domain.usecase.GameSessionUseCase
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.mapper.asDto
import ru.woowy.infrastructure.web.RestEndpoint

@RestController
@RequestMapping(RestEndpoint.BASE_URL)
class SessionController(
    private val gameSessionUseCase: GameSessionUseCase,
) {
    @GetMapping("${RestEndpoint.GET_SESSION}/{id}")
    suspend fun getSession(
        @PathVariable id: CharacterId,
    ): ResponseEntity<GameSessionDto>? {
        TODO()
    }

    @GetMapping(RestEndpoint.START_SESSION)
    suspend fun startSession(): ResponseEntity<GameSessionDto> {
        TODO()
    }
}