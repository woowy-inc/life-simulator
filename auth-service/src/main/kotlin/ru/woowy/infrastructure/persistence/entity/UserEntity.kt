package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import ru.woowy.id.UserId
import ru.woowy.security.UserRole

@Entity(name = "users")
class UserEntity(
    @Id
    var id: UserId,
    @Column(name = "username")
    var username: String,
    @Column(name = "email")
    var email: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "first_name")
    var firstName: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole,
    @Column(name = "is_email_verified")
    var isEmailVerified: Boolean,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserEntity) return false

        return this.id == other.id
    }

    override fun hashCode(): Int = this.id.hashCode()
}