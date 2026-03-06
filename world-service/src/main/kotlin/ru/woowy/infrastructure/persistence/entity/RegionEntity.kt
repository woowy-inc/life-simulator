package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import ru.woowy.domain.model.RegionId

@Entity(name = "regions")
class RegionEntity(
    @Id
    var id: RegionId,
    @Column(length = 20, nullable = false)
    var okato: String,
    @Column(length = 20, nullable = false)
    var oktmo: String,
    @Column(length = 10, nullable = false)
    var code: String,
    @Column(name = "iso_3166_2", length = 10, nullable = false)
    var iso31662: String,
    @Column(length = 255, nullable = false)
    var label: String,
    @Column(length = 255, nullable = false)
    var name: String,
    @Column(name = "name_en", length = 255, nullable = false)
    var nameEn: String,
    @Column(name = "full_name", length = 500, nullable = false)
    var fullName: String,
    @Column(name = "unofficial_name", length = 255, nullable = false)
    var unofficialName: String?,
    @Column(length = 100, nullable = false)
    var type: String,
    @Column(name = "type_short", length = 10, nullable = false)
    var typeShort: String,
    @Column(name = "content_type", length = 10, nullable = false)
    var contentType: String,
    @Column(nullable = false)
    var population: Long,
    @Column(name = "year_founded", length = 50, nullable = false)
    var yearFounded: String,
    @Column(nullable = false)
    var area: Int,
    @Column(length = 255, nullable = false)
    var district: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RegionEntity) return false

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}