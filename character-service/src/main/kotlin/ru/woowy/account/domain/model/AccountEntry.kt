package ru.woowy.account.domain.model

import ru.woowy.id.AccountEntryId
import ru.woowy.id.AccountId
import java.math.BigDecimal
import java.time.LocalDateTime

data class AccountEntry(
    val id: AccountEntryId,
    val accountId: AccountId,
    val amount: BigDecimal,
    val direction: EntryDirection,
    val reason: EntryReason,
    val occurredAt: LocalDateTime,
)