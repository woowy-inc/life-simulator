package ru.woowy.infrastructure.persistence.spec

import ru.woowy.infrastructure.persistence.entity.RegionEntity

object RegionSpecs {
    const val REGION = "region"

    val NAME = RegionEntity::name.name
    val NAME_EN = RegionEntity::nameEn.name
    val FULL_NAME = RegionEntity::fullName.name
    val UNOFFICIAL_NAME = RegionEntity::unofficialName.name
    val DISTRICT = RegionEntity::district.name
}