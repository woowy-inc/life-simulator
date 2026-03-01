package ru.woowy.user.infrastructure.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity(name = "email_verification_keys")
internal data class EmailVerificationKeyEntity(
    @Id
    val key: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,
    val expiresAt: LocalDateTime,
    val used: Boolean,
)