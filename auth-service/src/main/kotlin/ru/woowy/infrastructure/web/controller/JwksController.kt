package ru.woowy.infrastructure.web.controller

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.usecase.JwkUseCase

@Hidden
@RestController
@Validated
@RequestMapping("/.well-known")
class JwksController(
    private val jwkUseCase: JwkUseCase,
) {
    @GetMapping("/jwks.json")
    fun getJwks(): ResponseEntity<Map<String, Any>> = ResponseEntity.ok(jwkUseCase.getJwks())
}