package ru.woowy.account.application.usecase

import org.springframework.stereotype.Service
import ru.woowy.account.domain.model.Account
import ru.woowy.account.domain.model.AccountEntry
import ru.woowy.account.domain.repository.AccountEntryRepository
import ru.woowy.account.domain.repository.AccountRepository
import ru.woowy.account.domain.usecase.AccountUseCase
import ru.woowy.id.AccountId
import ru.woowy.id.CharacterId

@Service
class AccountUseCaseImpl(
    private val accountRepository: AccountRepository,
    private val accountEntryRepository: AccountEntryRepository,
) : AccountUseCase {
    override fun getAllAccounts(characterId: CharacterId): List<Account> = accountRepository.findAll(characterId)

    override fun getAccount(accountId: AccountId): Account? = accountRepository.findById(accountId)

    override fun addAccount(account: Account): Account = accountRepository.add(account)

    override fun updateAccount(account: Account): Account? = accountRepository.update(account)

    override fun deleteAccount(accountId: AccountId) = accountRepository.delete(accountId)

    override fun addEntry(entry: AccountEntry): AccountEntry = accountEntryRepository.add(entry)
}