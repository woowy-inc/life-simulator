package ru.woowy.user.infrastructure.persistence.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.woowy.user.infrastructure.persistence.entity.UserEntity
import java.util.UUID

@Repository
internal interface CrudUserRepository : CrudRepository<UserEntity, UUID> {
    fun existsByUsernameIgnoreCase(username: String): Boolean

    fun findUserByUsername(username: String): UserEntity?
}