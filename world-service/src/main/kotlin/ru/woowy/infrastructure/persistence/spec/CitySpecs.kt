package ru.woowy.infrastructure.persistence.spec

import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.domain.Specification
import ru.woowy.extension.queryFormat
import ru.woowy.infrastructure.extension.likeAny
import ru.woowy.infrastructure.persistence.entity.CityEntity
import ru.woowy.infrastructure.persistence.entity.RegionEntity

object CitySpecs {
    const val CITY = "city"

    const val CITY_NAME = "name"
    const val CITY_NAME_ALT = "nameAlt"
    const val CITY_NAME_EN = "nameEn"

    fun bySearch(search: String?): Specification<CityEntity> = Specification { city, select, builder ->
        if (search.isNullOrBlank()) return@Specification builder.conjunction()

        val pattern = search.queryFormat()
        val region = city.join<CityEntity, RegionEntity>(RegionSpecs.REGION, JoinType.LEFT)
        select.distinct(true)

        builder.likeAny(
            pattern,
            city.get(CITY_NAME),
            city.get(CITY_NAME_ALT),
            city.get(CITY_NAME_EN),
            region.get(RegionSpecs.REGION_NAME),
            region.get(RegionSpecs.REGION_NAME_EN),
            region.get(RegionSpecs.REGION_FULL_NAME),
            region.get(RegionSpecs.REGION_UNOFFICIAL_NAME),
            region.get(RegionSpecs.REGION_DISTRICT),
        )
    }
}