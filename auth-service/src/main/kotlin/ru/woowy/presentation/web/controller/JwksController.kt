package ru.woowy.presentation.web.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.service.AuthService

@RestController
@RequestMapping("/.well-known")
internal class JwksController(
    private val authService: AuthService,
) {
    @GetMapping("/jwks.json")
    fun getJwks(): Map<String, Any> = authService.getJwks()
}