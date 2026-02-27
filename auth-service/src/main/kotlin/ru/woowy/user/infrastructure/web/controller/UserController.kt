package ru.woowy.user.infrastructure.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.common.web.RestEndpoint
import ru.woowy.security.UserDto
import ru.woowy.user.application.usecase.GetUserByIdUseCase
import ru.woowy.user.application.usecase.IsUsernameAvailableUseCase
import ru.woowy.user.domain.model.UsernameAvailableDto
import ru.woowy.user.infrastructure.model.UserPrincipal

@Tag(name = "Users")
@RestController
@RequestMapping(RestEndpoint.BASE_URL)
internal class UserController(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val isUsernameAvailableUseCase: IsUsernameAvailableUseCase,
) {
    @Operation(summary = "Get current user profile", security = [])
    @GetMapping("${RestEndpoint.PROFILE_URL}/current")
    fun getCurrentProfile(
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(getUserByIdUseCase(principal.user.id))

    @Operation(
        summary = "Check username availability",
        description = "Returns whether the given username is already taken or available for registration",
        security = [],
    )
    @GetMapping("${RestEndpoint.USERNAME_AVAILABLE}/{username}")
    fun isUsernameAvailable(
        @PathVariable username: String,
    ): ResponseEntity<UsernameAvailableDto> = ResponseEntity.ok(isUsernameAvailableUseCase(username))
}