package ru.woowy.account.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.account.infrastructure.persistence.entity.AccountEntryEntity
import ru.woowy.id.AccountEntryId
import java.util.UUID

interface AccountEntryJpaRepository : JpaRepository<AccountEntryEntity, AccountEntryId> {
    fun findAllByAccountId(accountId: UUID): MutableList<AccountEntryEntity>
}