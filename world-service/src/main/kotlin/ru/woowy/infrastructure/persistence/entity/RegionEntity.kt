package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import ru.woowy.domain.model.RegionId

@Entity(name = "regions")
internal data class RegionEntity(
    @Id
    val id: RegionId,
    @Column(length = 20, nullable = false)
    val okato: String,
    @Column(length = 20, nullable = false)
    val oktmo: String,
    @Column(length = 10, nullable = false)
    val code: String,
    @Column("iso_3166_2", length = 10, nullable = false)
    val iso31662: String,
    @Column(length = 255, nullable = false)
    val label: String,
    @Column(length = 255, nullable = false)
    val name: String,
    @Column("name_en", length = 255, nullable = false)
    val nameEn: String,
    @Column("full_name", length = 500, nullable = false)
    val fullName: String,
    @Column("unofficial_name", length = 255, nullable = false)
    val unofficialName: String?,
    @Column(length = 100, nullable = false)
    val type: String,
    @Column("type_short", length = 10, nullable = false)
    val typeShort: String,
    @Column("content_type", length = 10, nullable = false)
    val contentType: String,
    @Column(nullable = false)
    val population: Long,
    @Column("year_founded", nullable = false)
    val yearFounded: Short,
    @Column(nullable = false)
    val area: Int,
    @Column(length = 255, nullable = false)
    val district: String,
//    @Column(length = 10, nullable = false)
//    val nameCase: RegionNameCase,
)