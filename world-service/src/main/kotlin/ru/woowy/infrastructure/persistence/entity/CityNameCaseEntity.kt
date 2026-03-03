package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import ru.woowy.domain.model.CityNameCaseId

@Entity(name = "city_namecases")
internal class CityNameCaseEntity(
    @Id
    var id: CityNameCaseId,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    var city: CityEntity,
    @Column(length = 255, nullable = false)
    var nominative: String,
    @Column(length = 255, nullable = false)
    var genitive: String,
    @Column(length = 255, nullable = false)
    var dative: String,
    @Column(length = 255, nullable = false)
    var accusative: String,
    @Column(length = 255, nullable = false)
    var ablative: String,
    @Column(length = 255, nullable = false)
    var prepositional: String,
    @Column(length = 255, nullable = false)
    var locative: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CityNameCaseEntity) return false

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
