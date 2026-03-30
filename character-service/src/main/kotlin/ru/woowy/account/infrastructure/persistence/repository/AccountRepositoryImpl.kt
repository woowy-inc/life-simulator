package ru.woowy.account.infrastructure.persistence.repository

import org.springframework.stereotype.Repository
import ru.woowy.account.domain.model.Account
import ru.woowy.account.domain.model.AccountType
import ru.woowy.account.domain.repository.AccountRepository
import ru.woowy.account.infrastructure.mapper.asDomain
import ru.woowy.account.infrastructure.persistence.entity.AccountEntity
import ru.woowy.account.infrastructure.persistence.jpa.AccountJpaRepository
import ru.woowy.character.infrastructure.persistence.entity.CharacterEntity
import ru.woowy.character.infrastructure.persistence.jpa.CharacterJpaRepository
import ru.woowy.id.AccountId
import ru.woowy.id.CharacterId
import kotlin.jvm.optionals.getOrNull

@Repository
class AccountRepositoryImpl(
    private val accountJpaRepository: AccountJpaRepository,
    private val characterJpaRepository: CharacterJpaRepository,
) : AccountRepository {
    override fun findAll(characterId: CharacterId): List<Account> =
        accountJpaRepository.findAllByCharacterId(characterId).asDomain()

    override fun findById(accountId: AccountId): Account? =
        accountJpaRepository.findById(accountId).getOrNull()?.asDomain()

    override fun findSalaryAccount(characterId: CharacterId): Account? =
        accountJpaRepository.findByCharacterIdAndType(characterId, AccountType.CASH)?.asDomain()

    override fun add(account: Account): Account = accountJpaRepository.save(account.asEntity()).asDomain()

    override fun update(account: Account): Account? = accountJpaRepository.save(account.asEntity()).asDomain()

    override fun delete(accountId: AccountId) = accountJpaRepository.deleteById(accountId)

    private fun Account.asEntity(): AccountEntity {
        val character = characterJpaRepository.getReferenceById(this.characterId)

        return asEntity(character)
    }

    private fun Account.asEntity(character: CharacterEntity): AccountEntity = AccountEntity(
        id = this.id,
        character = character,
        type = this.type,
        currency = this.currency,
        status = this.status,
        createdAt = this.createdAt,
    )
}