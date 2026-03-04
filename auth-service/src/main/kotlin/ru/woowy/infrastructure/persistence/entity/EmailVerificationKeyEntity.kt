package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity(name = "email_verification_keys")
class EmailVerificationKeyEntity(
    @Id
    var key: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity,
    var expiresAt: LocalDateTime,
    var used: Boolean,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EmailVerificationKeyEntity) return false

        return this.key == other.key
    }

    override fun hashCode(): Int = this.key.hashCode()
}