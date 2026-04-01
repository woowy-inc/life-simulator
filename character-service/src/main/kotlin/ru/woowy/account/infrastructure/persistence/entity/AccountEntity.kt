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
import ru.woowy.account.domain.model.AccountStatus
import ru.woowy.account.domain.model.AccountType
import ru.woowy.character.infrastructure.persistence.entity.CharacterEntity
import ru.woowy.id.AccountId
import java.time.LocalDateTime
import java.util.Currency

@Entity(name = "accounts")
class AccountEntity(
    @Id
    var id: AccountId,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    @OnDelete(OnDeleteAction.CASCADE)
    var character: CharacterEntity,
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var type: AccountType,
    @Column(nullable = false)
    var currency: Currency,
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var status: AccountStatus,
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AccountEntity) return false

        return this.id == other.id
    }

    override fun hashCode(): Int = this.id.hashCode()
}