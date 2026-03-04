package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.woowy.infrastructure.persistence.entity.UserEntity
import java.util.UUID

@Repository
interface JpaUserRepository : JpaRepository<UserEntity, UUID> {
    fun existsByUsernameIgnoreCase(username: String): Boolean

    fun findUserByUsername(username: String): UserEntity?
}