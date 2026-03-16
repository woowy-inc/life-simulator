package ru.woowy.account.domain.model

import ru.woowy.id.AccountId
import ru.woowy.id.CharacterId
import java.time.LocalDateTime
import java.util.Currency

data class Account(
    val id: AccountId,
    val characterId: CharacterId,
    val type: AccountType,
    val currency: Currency,
    val status: AccountStatus,
    val createdAt: LocalDateTime,
)