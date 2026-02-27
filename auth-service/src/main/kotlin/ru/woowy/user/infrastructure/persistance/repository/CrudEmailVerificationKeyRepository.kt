package ru.woowy.user.infrastructure.persistance.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.woowy.user.infrastructure.persistance.entity.EmailVerificationKeyEntity
import java.util.UUID

@Repository
internal interface CrudEmailVerificationKeyRepository : CrudRepository<EmailVerificationKeyEntity, UUID> {
    @EntityGraph(attributePaths = ["user"])
    fun findByKey(key: String): EmailVerificationKeyEntity?
}