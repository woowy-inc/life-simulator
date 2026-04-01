package ru.woowy.infrastructure.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.model.Need
import ru.woowy.domain.usecase.NeedUseCase
import ru.woowy.id.CharacterId
import ru.woowy.infrastructure.web.RestEndpoint

@RestController
@RequestMapping(RestEndpoint.INTERNAL_BASE_URL)
class NeedController(
    private val needUseCase: NeedUseCase,
) {
    @PostMapping("/character")
    suspend fun getNeeds(
        @RequestBody characterIds: Array<CharacterId>,
    ): ResponseEntity<Map<CharacterId, Need>> = ResponseEntity.ok(needUseCase.getNeeds(characterIds.toList()))
}