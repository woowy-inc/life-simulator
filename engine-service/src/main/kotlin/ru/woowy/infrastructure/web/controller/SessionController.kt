package ru.woowy.infrastructure.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.model.GameSessionDto
import ru.woowy.domain.model.UserPrincipal
import ru.woowy.domain.usecase.GameSessionUseCase
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.mapper.asDto
import ru.woowy.infrastructure.web.RestEndpoint

@RestController
@RequestMapping(RestEndpoint.BASE_URL)
class SessionController(
    private val gameSessionUseCase: GameSessionUseCase,
) {
    @GetMapping("/{id}")
    suspend fun getSession(
        @PathVariable id: CharacterId,
    ): ResponseEntity<GameSessionDto>? = ResponseEntity.ok(gameSessionUseCase.get(id)?.asDto())

    @PostMapping("/{id}${RestEndpoint.START_SESSION}")
    suspend fun startSession(
        @PathVariable id: CharacterId,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<GameSessionDto> = ResponseEntity.ok(gameSessionUseCase.start(id, principal.userId).asDto())

    @PostMapping("/{id}${RestEndpoint.STOP_SESSION}")
    suspend fun stopSession(
        @PathVariable id: CharacterId,
    ): ResponseEntity<GameSessionDto>? = ResponseEntity.ok(gameSessionUseCase.stop(id)?.asDto())
}