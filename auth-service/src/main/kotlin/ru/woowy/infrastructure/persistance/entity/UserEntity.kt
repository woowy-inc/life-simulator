package ru.woowy.infrastructure.persistance.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import ru.woowy.security.UserRole
import java.util.UUID

typealias UserId = UUID

@Entity(name = "users")
internal data class UserEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    @Column(name = "username")
    val username: String,
    @Column(name = "email")
    val email: String,
    @Column(name = "password")
    val password: String,
    @Column(name = "first_name")
    val firstName: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,
    @Column(name = "is_email_verified")
    val isEmailVerified: Boolean,
)