package ru.woowy.auth.infrastructure.web.controller

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
import ru.woowy.auth.application.usecase.RefreshAccessTokenUseCase
import ru.woowy.auth.application.usecase.UserLoginByUsernameUseCase
import ru.woowy.auth.domain.model.RefreshTokenRequest
import ru.woowy.auth.domain.model.TokenDto
import ru.woowy.common.web.RestEndpoint
import ru.woowy.security.UserDto
import ru.woowy.user.application.usecase.UserRegisterUseCase
import ru.woowy.user.application.usecase.VerifyUserEmailUseCase
import ru.woowy.user.domain.model.UserRegisterRequest
import ru.woowy.user.domain.model.UsernameRequest

@Tag(name = "Auth")
@RestController
@Validated
@RequestMapping(RestEndpoint.BASE_URL)
internal class AuthController(
    private val userLoginByUsernameUseCase: UserLoginByUsernameUseCase,
    private val userRegisterUseCase: UserRegisterUseCase,
    private val verifyUserEmailUseCase: VerifyUserEmailUseCase,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase,
) {
    @Operation(summary = "Login by username", security = [])
    @PostMapping("${RestEndpoint.LOGIN_URL}/username")
    fun loginByUsername(
        @RequestBody @Valid request: UsernameRequest,
    ): ResponseEntity<TokenDto> = ResponseEntity.ok(userLoginByUsernameUseCase(request))

    @Operation(summary = "Refresh access token", security = [])
    @PostMapping("${RestEndpoint.TOKEN_URL}/refresh")
    fun refreshToken(
        @RequestBody @Valid request: RefreshTokenRequest,
    ): ResponseEntity<TokenDto> = ResponseEntity.ok(refreshAccessTokenUseCase(request))

    @Operation(summary = "Register user", security = [])
    @PostMapping(RestEndpoint.REGISTER_URL)
    fun registerUser(
        @RequestBody @Valid request: UserRegisterRequest,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(userRegisterUseCase(request))

    @Operation(summary = "Verify user email", security = [])
    @PostMapping(RestEndpoint.EMAIL_VERIFY)
    fun verifyUserEmail(
        @RequestParam key: String,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(verifyUserEmailUseCase(key))
}