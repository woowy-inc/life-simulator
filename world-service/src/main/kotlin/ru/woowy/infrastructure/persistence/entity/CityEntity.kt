package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import ru.woowy.domain.model.CityId

@Entity(name = "cities")
internal class CityEntity(
    @Id
    var id: CityId,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    var region: RegionEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timezone_id", nullable = false)
    var timezone: TimezoneEntity,
    @Column(length = 20, nullable = false)
    var okato: String,
    @Column(length = 20, nullable = false)
    var oktmo: String,
    @Column(length = 255, nullable = false)
    var label: String,
    @Column(length = 255, nullable = false)
    var name: String,
    @Column(name = "name_alt", length = 255, nullable = false)
    var nameAlt: String,
    @Column(name = "name_en", length = 255, nullable = false)
    var nameEn: String,
    @Column(length = 100, nullable = false)
    var type: String,
    @Column(name = "type_short", length = 10, nullable = false)
    var typeShort: String,
    @Column(name = "content_type", length = 10, nullable = false)
    var contentType: String,
    @Column(name = "is_dual_name", nullable = false)
    var isDualName: Boolean,
    @Column(name = "is_capital", nullable = false)
    var isCapital: Boolean,
    @Column(nullable = false)
    var zip: Int,
    @Column(nullable = false)
    var population: Long,
    @Column(name = "year_founded", nullable = false)
    var yearFounded: Short,
    @Column(name = "year_city_status", nullable = false)
    var yearCityStatus: Short,
    @Column(nullable = false)
    var latitude: Double,
    @Column(nullable = false)
    var longitude: Double,
) {
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "city")
    var nameCase: CityNameCaseEntity? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CityEntity) return false

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}