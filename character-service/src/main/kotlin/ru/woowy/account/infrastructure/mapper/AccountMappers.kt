package ru.woowy.account.infrastructure.mapper

import ru.woowy.account.domain.model.Account
import ru.woowy.account.domain.model.AccountEntry
import ru.woowy.account.infrastructure.persistence.entity.AccountEntity
import ru.woowy.account.infrastructure.persistence.entity.AccountEntryEntity

fun AccountEntity.asDomain(): Account = Account(
    id = this.id,
    characterId = this.character.id,
    type = this.type,
    currency = this.currency,
    status = this.status,
    createdAt = this.createdAt,
)

fun AccountEntryEntity.asDomain(): AccountEntry = AccountEntry(
    id = this.id,
    accountId = this.account.id,
    amount = this.amount,
    direction = this.direction,
    reason = this.reason,
    occurredAt = this.occurredAt,
)

@JvmName("asDomainAccountEntity")
fun Iterable<AccountEntity>.asDomain(): List<Account> = this.map { it.asDomain() }

@JvmName("asDomainAccountEntryEntity")
fun Iterable<AccountEntryEntity>.asDomain(): List<AccountEntry> = this.map { it.asDomain() }