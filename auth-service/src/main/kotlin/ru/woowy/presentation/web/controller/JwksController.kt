package ru.woowy.presentation.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.application.usecase.GetJwksUseCase

@RestController
@RequestMapping("/.well-known")
internal class JwksController(
    private val getJwksUseCase: GetJwksUseCase,
) {
    @GetMapping("/jwks.json")
    fun getJwks(): ResponseEntity<Map<String, Any>> = ResponseEntity.ok(getJwksUseCase())
}