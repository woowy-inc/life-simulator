package ru.woowy.presentation.web.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.model.TokenResponse
import ru.woowy.domain.model.UsernameRequest
import ru.woowy.domain.service.AuthService

private const val BASE_ENDPOINT = "/auth"

@RestController
@RequestMapping(BASE_ENDPOINT)
internal class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login/username")
    fun authByUsername(
        @RequestBody request: UsernameRequest,
    ): TokenResponse = authService.authByUsername(request)
}