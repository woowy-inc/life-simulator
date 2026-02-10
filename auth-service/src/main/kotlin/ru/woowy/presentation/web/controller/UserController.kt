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
import ru.woowy.application.usecase.GetUserByIdUseCase
import ru.woowy.application.usecase.RefreshAccessTokenUseCase
import ru.woowy.application.usecase.UserLoginByUsernameUseCase
import ru.woowy.application.usecase.UserRegisterUseCase
import ru.woowy.domain.model.RefreshTokenRequest
import ru.woowy.domain.model.TokenDto
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.model.UsernameRequest
import ru.woowy.infrastructure.model.UserPrincipal
import ru.woowy.security.UserDto

@RestController
@RequestMapping(RestEndpoint.BASE_URL)
internal class UserController(
    private val userLoginByUsernameUseCase: UserLoginByUsernameUseCase,
    private val userRegisterUseCase: UserRegisterUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase,
) {
    @Operation(summary = "Login by username", security = [])
    @PostMapping("${RestEndpoint.LOGIN_URL}/username")
    fun loginByUsername(
        @RequestBody request: UsernameRequest,
    ): ResponseEntity<TokenDto> = ResponseEntity.ok(userLoginByUsernameUseCase(request))

    @Operation(summary = "Refresh access token", security = [])
    @PostMapping("${RestEndpoint.TOKEN_URL}/refresh")
    fun refreshToken(
        @RequestBody request: RefreshTokenRequest,
    ): ResponseEntity<TokenDto> = ResponseEntity.ok(refreshAccessTokenUseCase(request))

    @Operation(summary = "Register user", security = [])
    @PostMapping(RestEndpoint.REGISTER_URL)
    fun registerUser(
        @RequestBody request: UserRegisterRequest,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(userRegisterUseCase(request))

    @Operation(summary = "Get current user profile", security = [])
    @GetMapping("${RestEndpoint.PROFILE_URL}/current")
    fun getCurrentProfile(
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(getUserByIdUseCase(principal.user.id))
}