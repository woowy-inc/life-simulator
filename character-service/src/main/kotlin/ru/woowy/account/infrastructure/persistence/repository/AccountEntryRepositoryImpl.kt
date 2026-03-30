package ru.woowy.account.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import ru.woowy.account.domain.model.AccountEntry
import ru.woowy.account.domain.repository.AccountEntryRepository
import ru.woowy.account.infrastructure.mapper.asDomain
import ru.woowy.account.infrastructure.persistence.entity.AccountEntity
import ru.woowy.account.infrastructure.persistence.entity.AccountEntryEntity
import ru.woowy.account.infrastructure.persistence.jpa.AccountEntryJpaRepository
import ru.woowy.account.infrastructure.persistence.jpa.AccountJpaRepository
import ru.woowy.id.AccountEntryId
import ru.woowy.id.AccountId
import kotlin.jvm.optionals.getOrNull

@Repository
class AccountEntryRepositoryImpl(
    private val accountEntryJpaRepository: AccountEntryJpaRepository,
    private val accountJpaRepository: AccountJpaRepository,
) : AccountEntryRepository {
    override fun findAll(accountId: AccountId): List<AccountEntry> =
        accountEntryJpaRepository.findAllByAccountId(accountId).asDomain()

    override fun findById(accountEntryId: AccountEntryId): AccountEntry? =
        accountEntryJpaRepository.findById(accountEntryId).getOrNull()?.asDomain()

    override fun add(entry: AccountEntry): AccountEntry = accountEntryJpaRepository.save(entry.asEntity()).asDomain()

    override fun update(entry: AccountEntry): AccountEntry? =
        accountEntryJpaRepository.save(entry.asEntity()).asDomain()

    override fun delete(accountEntryId: AccountEntryId) = accountEntryJpaRepository.deleteById(accountEntryId)

    fun AccountEntry.asEntity(): AccountEntryEntity {
        val account = accountJpaRepository.getReferenceById(this.accountId)

        return asEntity(account)
    }

    private fun AccountEntry.asEntity(account: AccountEntity): AccountEntryEntity = AccountEntryEntity(
        id = this.id,
        account = account,
        amount = this.amount,
        direction = this.direction,
        reason = this.reason,
        occurredAt = this.occurredAt,
    )
}