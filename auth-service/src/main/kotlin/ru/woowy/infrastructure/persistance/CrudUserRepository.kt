package ru.woowy.infrastructure.persistance

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.woowy.infrastructure.persistance.entity.UserEntity
import java.util.UUID

@Repository
internal interface CrudUserRepository : CrudRepository<UserEntity, UUID> {
    fun findUserByUsername(username: String): UserEntity?
}