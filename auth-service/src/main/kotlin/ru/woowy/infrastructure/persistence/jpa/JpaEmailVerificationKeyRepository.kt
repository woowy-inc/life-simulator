package ru.woowy.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.woowy.infrastructure.persistence.entity.EmailVerificationKeyEntity
import java.util.UUID

@Repository
interface JpaEmailVerificationKeyRepository : JpaRepository<EmailVerificationKeyEntity, UUID> {
    @EntityGraph(attributePaths = ["user"])
    fun findByKey(key: String): EmailVerificationKeyEntity?
}