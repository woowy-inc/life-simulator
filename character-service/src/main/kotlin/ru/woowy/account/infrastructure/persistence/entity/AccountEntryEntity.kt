package ru.woowy.account.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import ru.woowy.account.domain.model.EntryDirection
import ru.woowy.account.domain.model.EntryReason
import ru.woowy.id.AccountEntryId
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(name = "account_entries")
class AccountEntryEntity(
    @Id
    var id: AccountEntryId,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(OnDeleteAction.CASCADE)
    var account: AccountEntity,
    @Column(nullable = false)
    var amount: BigDecimal,
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var direction: EntryDirection,
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var reason: EntryReason,
    @Column(name = "occurred_at", nullable = false)
    var occurredAt: LocalDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AccountEntryEntity) return false

        return this.id == other.id
    }

    override fun hashCode(): Int = this.id.hashCode()
}