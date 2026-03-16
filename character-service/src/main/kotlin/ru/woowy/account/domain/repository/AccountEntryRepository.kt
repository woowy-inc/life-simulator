package ru.woowy.account.domain.repository

import ru.woowy.account.domain.model.AccountEntry
import ru.woowy.id.AccountEntryId
import ru.woowy.id.AccountId

interface AccountEntryRepository {
    fun findAll(accountId: AccountId): List<AccountEntry>

    fun findById(accountEntryId: AccountEntryId): AccountEntry?

    fun add(entry: AccountEntry): AccountEntry

    fun update(entry: AccountEntry): AccountEntry?

    fun delete(accountEntryId: AccountEntryId)
}