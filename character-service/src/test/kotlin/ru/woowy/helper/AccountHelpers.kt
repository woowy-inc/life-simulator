package ru.woowy.helper

import ru.woowy.account.domain.model.Account
import ru.woowy.account.domain.model.AccountEntry
import ru.woowy.account.domain.model.AccountStatus
import ru.woowy.account.domain.model.AccountType
import ru.woowy.account.domain.model.EntryDirection
import ru.woowy.account.domain.model.EntryReason
import ru.woowy.id.AccountEntryId
import ru.woowy.id.AccountId
import ru.woowy.id.CharacterId
import ru.woowy.util.randomDouble
import ru.woowy.util.randomLocalDateTime
import ru.woowy.util.randomUUID
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.Currency

fun randomAccount(
    id: AccountId = randomUUID(),
    characterId: CharacterId = randomUUID(),
    type: AccountType = AccountType.CASH,
    currency: Currency = Currency.getInstance("RUB"),
    status: AccountStatus = AccountStatus.ACTIVE,
    createdAt: LocalDateTime = randomLocalDateTime(),
): Account = Account(
    id = id,
    characterId = characterId,
    type = type,
    currency = currency,
    status = status,
    createdAt = createdAt,
)

fun randomAccountEntry(
    id: AccountEntryId = randomUUID(),
    accountId: AccountId = randomUUID(),
    amount: BigDecimal = randomDouble().toBigDecimal(),
    direction: EntryDirection = EntryDirection.CREDIT,
    reason: EntryReason = EntryReason.OTHER,
    occurredAt: LocalDateTime = randomLocalDateTime(),
): AccountEntry = AccountEntry(
    id = id,
    accountId = accountId,
    amount = amount,
    direction = direction,
    reason = reason,
    occurredAt = occurredAt,
)