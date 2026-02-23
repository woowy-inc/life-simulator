package ru.woowy.domain.model

import ru.woowy.security.User
import java.time.OffsetDateTime

data class EmailVerificationKey(
    val key: String,
    val user: User,
    val expiresAt: OffsetDateTime,
    val used: Boolean,
)