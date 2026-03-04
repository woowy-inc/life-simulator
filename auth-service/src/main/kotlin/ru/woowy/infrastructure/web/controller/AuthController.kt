package ru.woowy.infrastructure.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.model.RefreshTokenRequest
import ru.woowy.domain.model.TokenDto
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.model.UsernameRequest
import ru.woowy.domain.usecase.AuthUseCase
import ru.woowy.domain.usecase.UserUseCase
import ru.woowy.infrastructure.web.RestEndpoint
import ru.woowy.security.UserDto

@Tag(name = "Auth")
@RestController
@Validated
@RequestMapping(RestEndpoint.BASE_URL)
class AuthController(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase,
) {
    @Operation(summary = "Login by username", security = [])
    @PostMapping("${RestEndpoint.LOGIN_URL}/username")
    fun loginByUsername(
        @RequestBody @Valid request: UsernameRequest,
    ): ResponseEntity<TokenDto> = ResponseEntity.ok(authUseCase.loginByUsername(request))

    @Operation(summary = "Refresh access token", security = [])
    @PostMapping("${RestEndpoint.TOKEN_URL}/refresh")
    fun refreshToken(
        @RequestBody @Valid request: RefreshTokenRequest,
    ): ResponseEntity<TokenDto> = ResponseEntity.ok(authUseCase.refreshAccessToken(request))

    @Operation(summary = "Register user", security = [])
    @PostMapping(RestEndpoint.REGISTER_URL)
    fun registerUser(
        @RequestBody @Valid request: UserRegisterRequest,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(userUseCase.register(request))

    @Operation(summary = "Verify user email", security = [])
    @PostMapping(RestEndpoint.EMAIL_VERIFY)
    fun verifyUserEmail(
        @RequestParam key: String,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(userUseCase.verifyEmail(key))
}