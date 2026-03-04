package ru.woowy.infrastructure.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.model.UserPrincipal
import ru.woowy.domain.model.UsernameAvailableDto
import ru.woowy.domain.usecase.UserUseCase
import ru.woowy.infrastructure.mapper.asDto
import ru.woowy.infrastructure.web.RestEndpoint
import ru.woowy.security.UserDto

@Tag(name = "Users")
@RestController
@Validated
@RequestMapping(RestEndpoint.BASE_URL)
class UserController(
    private val userUseCase: UserUseCase,
) {
    @Operation(summary = "Get current user profile")
    @GetMapping("${RestEndpoint.PROFILE_URL}/current")
    fun getCurrentProfile(
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(userUseCase.getByUserId(principal.userId)?.asDto())

    @Operation(
        summary = "Check username availability",
        description = "Returns whether the given username is already taken or available for registration",
        security = [],
    )
    @GetMapping("${RestEndpoint.USERNAME_AVAILABLE}/{username}")
    fun isUsernameAvailable(
        @PathVariable username: String,
    ): ResponseEntity<UsernameAvailableDto> = ResponseEntity.ok(userUseCase.isUsernameAvailable(username))
}