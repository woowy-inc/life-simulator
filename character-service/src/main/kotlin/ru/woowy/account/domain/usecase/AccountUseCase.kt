package ru.woowy.account.domain.usecase

import ru.woowy.account.domain.model.Account
import ru.woowy.account.domain.model.AccountEntry
import ru.woowy.account.domain.model.AccountType
import ru.woowy.id.AccountId
import ru.woowy.id.CharacterId
import java.util.Currency

interface AccountUseCase {
    fun getSalaryAccount(characterId: CharacterId): Account

    fun getAllAccounts(characterId: CharacterId): List<Account>

    fun getAccount(accountId: AccountId): Account?

    fun addAccount(account: Account): Account

    fun addAccount(
        characterId: CharacterId,
        type: AccountType,
        currency: Currency,
    ): Account

    fun updateAccount(account: Account): Account?

    fun deleteAccount(accountId: AccountId)

    fun addEntry(entry: AccountEntry): AccountEntry
}