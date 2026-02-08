package ru.woowy.presentation.web.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.application.config.RestEndpoint
import ru.woowy.domain.model.TokenResponse
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.model.UsernameRequest
import ru.woowy.domain.service.AuthService
import ru.woowy.domain.service.UserService
import ru.woowy.infrastructure.model.UserPrincipal
import ru.woowy.security.UserDto

@RestController
@RequestMapping(RestEndpoint.BASE_URL)
internal class UserController(
    private val authService: AuthService,
    private val userService: UserService,
) {
    @Operation(summary = "Login by username", security = [])
    @PostMapping("${RestEndpoint.LOGIN_URL}/username")
    fun loginByUsername(
        @RequestBody request: UsernameRequest,
    ): TokenResponse = authService.loginByUsername(request)

    @PostMapping(RestEndpoint.REGISTER_URL)
    fun registerUser(
        @RequestBody request: UserRegisterRequest,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(authService.registerUser(request))

    @GetMapping("${RestEndpoint.PROFILE_URL}/current")
    fun getCurrentProfile(
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<UserDto> {
        val response = userService.getUser(principal.user.id)

        return ResponseEntity.ok(response)
    }
}