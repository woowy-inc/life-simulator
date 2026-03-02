package ru.woowy.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import ru.woowy.domain.model.RegionId

@Entity(name = "region_namecases")
internal data class RegionNameCaseEntity(
    @Id
    @Column("region_id", nullable = false)
    val regionId: RegionId,
    @Column(length = 255, nullable = false)
    val nominative: String,
    @Column(length = 255, nullable = false)
    val genitive: String,
    @Column(length = 255, nullable = false)
    val dative: String,
    @Column(length = 255, nullable = false)
    val accusative: String,
    @Column(length = 255, nullable = false)
    val ablative: String,
    @Column(length = 255, nullable = false)
    val prepositional: String,
    @Column(length = 255, nullable = false)
    val locative: String,
)