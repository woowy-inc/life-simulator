package ru.woowy.account.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import ru.woowy.account.infrastructure.persistence.entity.AccountEntity
import ru.woowy.id.AccountId
import java.util.UUID

interface AccountJpaRepository : JpaRepository<AccountEntity, AccountId> {
    fun findAllByCharacterId(characterId: UUID): MutableList<AccountEntity>
}