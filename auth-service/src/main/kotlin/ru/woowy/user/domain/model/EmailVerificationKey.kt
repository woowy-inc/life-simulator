package ru.woowy.user.domain.model

import ru.woowy.security.User
import java.time.LocalDateTime

data class EmailVerificationKey(
    val key: String,
    val user: User,
    val expiresAt: LocalDateTime,
    val used: Boolean,
)