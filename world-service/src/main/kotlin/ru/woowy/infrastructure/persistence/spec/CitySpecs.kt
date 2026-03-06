package ru.woowy.infrastructure.persistence.spec

import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.domain.Specification
import ru.woowy.extension.queryFormat
import ru.woowy.infrastructure.extension.likeAny
import ru.woowy.infrastructure.persistence.entity.CityEntity
import ru.woowy.infrastructure.persistence.entity.RegionEntity

object CitySpecs {
    const val CITY = "city"

    val NAME = CityEntity::name.name
    val NAME_ALT = CityEntity::nameAlt.name
    val NAME_EN = CityEntity::nameEn.name

    fun bySearch(search: String?): Specification<CityEntity> = Specification { city, select, builder ->
        if (search.isNullOrBlank()) return@Specification builder.conjunction()

        val pattern = search.queryFormat()
        val region = city.join<CityEntity, RegionEntity>(RegionSpecs.REGION, JoinType.LEFT)
        select.distinct(true)

        builder.likeAny(
            pattern,
            city.get(NAME),
            city.get(NAME_ALT),
            city.get(NAME_EN),
            region.get(RegionSpecs.NAME),
            region.get(RegionSpecs.NAME_EN),
            region.get(RegionSpecs.FULL_NAME),
            region.get(RegionSpecs.UNOFFICIAL_NAME),
            region.get(RegionSpecs.DISTRICT),
        )
    }
}