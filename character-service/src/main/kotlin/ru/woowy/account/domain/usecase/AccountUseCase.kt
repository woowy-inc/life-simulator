package ru.woowy.account.domain.usecase

import ru.woowy.account.domain.model.Account
import ru.woowy.account.domain.model.AccountEntry
import ru.woowy.id.AccountId
import ru.woowy.id.CharacterId

interface AccountUseCase {
    fun getAllAccounts(characterId: CharacterId): List<Account>

    fun getAccount(accountId: AccountId): Account?

    fun addAccount(account: Account): Account

    fun updateAccount(account: Account): Account?

    fun deleteAccount(accountId: AccountId)

    fun addEntry(entry: AccountEntry): AccountEntry
}