package ru.woowy.infrastructure.persistance.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.woowy.infrastructure.persistance.entity.UserEntity
import java.util.UUID

@Repository
internal interface CrudUserRepository : CrudRepository<UserEntity, UUID> {
    fun existsByUsername(username: String): Boolean

    fun findUserByUsername(username: String): UserEntity?
}