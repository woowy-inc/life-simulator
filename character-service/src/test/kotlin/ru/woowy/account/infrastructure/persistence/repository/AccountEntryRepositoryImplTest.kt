package ru.woowy.account.infrastructure.persistence.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.account.domain.model.AccountEntry
import ru.woowy.account.infrastructure.persistence.jpa.AccountEntryJpaRepository
import ru.woowy.account.infrastructure.persistence.jpa.AccountJpaRepository
import ru.woowy.character.infrastructure.persistence.jpa.CharacterJpaRepository
import ru.woowy.character.infrastructure.persistence.repository.CharacterRepositoryImpl
import ru.woowy.common.JpaRepositoryTest
import ru.woowy.helper.randomAccount
import ru.woowy.helper.randomAccountEntry
import ru.woowy.helper.randomCharacter
import ru.woowy.util.randomUUID

class AccountEntryRepositoryImplTest
    @Autowired
    constructor(
        private val characterJpaRepository: CharacterJpaRepository,
        private val accountJpaRepository: AccountJpaRepository,
        private val accountEntryJpaRepository: AccountEntryJpaRepository,
    ) : JpaRepositoryTest() {
        private val characterRepository = CharacterRepositoryImpl(characterJpaRepository)
        private val accountRepository = AccountRepositoryImpl(accountJpaRepository, characterJpaRepository)
        private val repository = AccountEntryRepositoryImpl(accountEntryJpaRepository, accountJpaRepository)

        @BeforeEach
        fun setUp() {
            accountEntryJpaRepository.deleteAll()
            accountJpaRepository.deleteAll()
            characterJpaRepository.deleteAll()
        }

        @Test
        fun `entry should be added`() {
            val character = characterRepository.add(randomCharacter())
            val account = accountRepository.add(randomAccount(characterId = character.id))
            val entry = randomAccountEntry(accountId = account.id)

            val actual = repository.add(entry)

            assertEquals(entry, actual)
        }

        @Test
        fun `entry should be found by id`() {
            val character = characterRepository.add(randomCharacter())
            val account = accountRepository.add(randomAccount(characterId = character.id))
            val entry = repository.add(randomAccountEntry(accountId = account.id))

            val actual = repository.findById(entry.id)

            assertEquals(entry, actual)
        }

        @Test
        fun `entry should not be found by id`() {
            val actual = repository.findById(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `entries should be found by account`() {
            val character = characterRepository.add(randomCharacter())
            val account = accountRepository.add(randomAccount(characterId = character.id))
            val first = repository.add(randomAccountEntry(accountId = account.id))
            val second = repository.add(randomAccountEntry(accountId = account.id))
            val otherAccount = accountRepository.add(randomAccount(characterId = character.id))
            repository.add(randomAccountEntry(accountId = otherAccount.id))

            val actual = repository.findAll(account.id)

            assertEquals(listOf(first, second), actual)
        }

        @Test
        fun `entries should return empty list for unknown account`() {
            val actual = repository.findAll(randomUUID())

            assertEquals(emptyList<AccountEntry>(), actual)
        }

        @Test
        fun `entry should be deleted`() {
            val character = characterRepository.add(randomCharacter())
            val account = accountRepository.add(randomAccount(characterId = character.id))
            val saved = repository.add(randomAccountEntry(accountId = account.id))

            repository.delete(saved.id)

            assertNull(repository.findById(saved.id))
        }
    }