package ru.woowy.account.infrastructure.persistence.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.account.domain.model.Account
import ru.woowy.account.domain.model.AccountStatus
import ru.woowy.account.infrastructure.persistence.jpa.AccountEntryJpaRepository
import ru.woowy.account.infrastructure.persistence.jpa.AccountJpaRepository
import ru.woowy.character.infrastructure.persistence.jpa.CharacterJpaRepository
import ru.woowy.character.infrastructure.persistence.repository.CharacterRepositoryImpl
import ru.woowy.common.JpaRepositoryTest
import ru.woowy.helper.randomAccount
import ru.woowy.helper.randomCharacter
import ru.woowy.util.randomUUID

class AccountRepositoryImplTest
    @Autowired
    constructor(
        private val characterJpaRepository: CharacterJpaRepository,
        private val accountJpaRepository: AccountJpaRepository,
        private val accountEntryJpaRepository: AccountEntryJpaRepository,
    ) : JpaRepositoryTest() {
        private val characterRepository = CharacterRepositoryImpl(characterJpaRepository)
        private val repository = AccountRepositoryImpl(accountJpaRepository, characterJpaRepository)

        @BeforeEach
        fun setUp() {
            accountJpaRepository.deleteAll()
            characterJpaRepository.deleteAll()
        }

        @Test
        fun `account should be added`() {
            val character = characterRepository.add(randomCharacter())
            val account = randomAccount(characterId = character.id)

            val actual = repository.add(account)

            assertEquals(account, actual)
        }

        @Test
        fun `account should be found by id`() {
            val character = characterRepository.add(randomCharacter())
            val account = repository.add(randomAccount(characterId = character.id))

            val actual = repository.findById(account.id)

            assertEquals(account, actual)
        }

        @Test
        fun `account should not be found by id`() {
            val actual = repository.findById(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `accounts should be found by character`() {
            val character = characterRepository.add(randomCharacter())
            val first = repository.add(randomAccount(characterId = character.id))
            val second = repository.add(randomAccount(characterId = character.id))
            val otherCharacter = characterRepository.add(randomCharacter())
            repository.add(randomAccount(characterId = otherCharacter.id))

            val actual = repository.findAll(character.id)

            assertEquals(listOf(first, second), actual)
        }

        @Test
        fun `accounts should return empty list for unknown character`() {
            val actual = repository.findAll(randomUUID())

            assertEquals(emptyList<Account>(), actual)
        }

        @Test
        fun `account should be updated`() {
            val character = characterRepository.add(randomCharacter())
            val saved = repository.add(randomAccount(characterId = character.id))
            val updated = saved.copy(status = AccountStatus.FROZEN)

            val actual = repository.update(updated)

            assertEquals(updated, actual)
        }

        @Test
        fun `account should be deleted`() {
            val character = characterRepository.add(randomCharacter())
            val saved = repository.add(randomAccount(characterId = character.id))

            repository.delete(saved.id)

            assertNull(repository.findById(saved.id))
        }
    }