package ru.woowy.domain.model

import ru.woowy.security.User
import java.time.Instant
import java.util.UUID

internal data class RefreshToken(
    val id: UUID = UUID.randomUUID(),
    val token: String,
    val user: User,
    val expiresAt: Instant,
    val createdAt: Instant = Instant.now(),
    var revokedAt: Instant? = null,
) {
    fun isExpired(): Boolean = Instant.now().isAfter(expiresAt)

    fun isRevoked(): Boolean = revokedAt != null

    fun isValid(): Boolean = !isExpired() && !isRevoked()
}