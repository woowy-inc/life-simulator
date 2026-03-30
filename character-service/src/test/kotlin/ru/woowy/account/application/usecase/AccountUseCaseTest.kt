package ru.woowy.account.application.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import ru.woowy.account.domain.model.Account
import ru.woowy.account.domain.repository.AccountEntryRepository
import ru.woowy.account.domain.repository.AccountRepository
import ru.woowy.helper.randomAccount
import ru.woowy.helper.randomAccountEntry
import ru.woowy.util.randomUUID

class AccountUseCaseTest {
    private val accountRepository = mockk<AccountRepository>(relaxed = true)
    private val accountEntryRepository = mockk<AccountEntryRepository>(relaxed = true)
    private val useCase = AccountUseCaseImpl(accountRepository, accountEntryRepository)

    private val characterId = randomUUID()
    private val account = randomAccount(characterId = characterId)
    private val entry = randomAccountEntry(accountId = account.id)

    @Test
    fun `getAllAccounts - returns accounts for character`() {
        every { accountRepository.findAll(characterId) } returns listOf(account)

        val result = useCase.getAllAccounts(characterId)

        assertEquals(listOf(account), result)
        verify(exactly = 1) { accountRepository.findAll(characterId) }
    }

    @Test
    fun `getAllAccounts - returns empty list when no accounts`() {
        every { accountRepository.findAll(characterId) } returns emptyList()

        assertEquals(emptyList<Account>(), useCase.getAllAccounts(characterId))
    }

    @Test
    fun `getAccount - found`() {
        every { accountRepository.findById(account.id) } returns account

        assertEquals(account, useCase.getAccount(account.id))
    }

    @Test
    fun `getAccount - not found - returns null`() {
        every { accountRepository.findById(account.id) } returns null

        assertNull(useCase.getAccount(account.id))
    }

    @Test
    fun `addAccount - saves and returns account`() {
        every { accountRepository.add(account) } returns account

        val result = useCase.addAccount(account)

        assertEquals(account, result)
        verify(exactly = 1) { accountRepository.add(account) }
    }

    @Test
    fun `updateAccount - returns updated account`() {
        every { accountRepository.update(account) } returns account

        val result = useCase.updateAccount(account)

        assertEquals(account, result)
        verify(exactly = 1) { accountRepository.update(account) }
    }

    @Test
    fun `updateAccount - not found - returns null`() {
        every { accountRepository.update(account) } returns null

        assertNull(useCase.updateAccount(account))
    }

    @Test
    fun `deleteAccount - delegates to repository`() {
        useCase.deleteAccount(account.id)

        verify(exactly = 1) { accountRepository.delete(account.id) }
    }

    @Test
    fun `addEntry - saves and returns entry`() {
        every { accountEntryRepository.add(entry) } returns entry

        val result = useCase.addEntry(entry)

        assertEquals(entry, result)
        verify(exactly = 1) { accountEntryRepository.add(entry) }
    }
}