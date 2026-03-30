package ru.woowy.account.domain.repository

import ru.woowy.account.domain.model.Account
import ru.woowy.id.AccountId
import ru.woowy.id.CharacterId

interface AccountRepository {
    fun findAll(characterId: CharacterId): List<Account>

    fun findById(accountId: AccountId): Account?

    fun findSalaryAccount(characterId: CharacterId): Account?

    fun add(account: Account): Account

    fun update(account: Account): Account?

    fun delete(accountId: AccountId)
}