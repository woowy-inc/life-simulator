package ru.woowy.user.domain.model

import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import ru.woowy.security.User
import java.time.LocalDateTime

data class EmailVerificationKey(
    @field:NotBlank("Key cannot be blank")
    val key: String,
    val user: User,
    @field:FutureOrPresent("Expires date should be in the future or present")
    val expiresAt: LocalDateTime,
    val used: Boolean,
)