package ru.woowy.presentation.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.application.config.RestEndpoint
import ru.woowy.application.usecase.GetUserByIdUseCase
import ru.woowy.infrastructure.model.UserPrincipal
import ru.woowy.security.UserDto

@Tag(name = "Users")
@RestController
@RequestMapping(RestEndpoint.BASE_URL)
internal class UserController(
    private val getUserByIdUseCase: GetUserByIdUseCase,
) {
    @Operation(summary = "Get current user profile", security = [])
    @GetMapping("${RestEndpoint.PROFILE_URL}/current")
    fun getCurrentProfile(
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(getUserByIdUseCase(principal.user.id))
}