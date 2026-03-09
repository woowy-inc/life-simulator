package ru.woowy.infrastructure.persistence.spec

import jakarta.persistence.criteria.JoinType
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import ru.woowy.extension.queryFormat
import ru.woowy.infrastructure.extension.likeAny
import ru.woowy.infrastructure.model.LocationSortColumn
import ru.woowy.infrastructure.persistence.entity.LocationEntity
import ru.woowy.infrastructure.persistence.entity.RegionEntity

object LocationSpecs {
    const val LOCATION = "location"

    val NAME = LocationEntity::name.name
    val NAME_ALT = LocationEntity::nameAlt.name
    val NAME_EN = LocationEntity::nameEn.name

    fun bySearch(search: String?): Specification<LocationEntity> = Specification { location, select, builder ->
        if (search.isNullOrBlank()) return@Specification builder.conjunction()

        val pattern = search.queryFormat()
        val region = location.join<LocationEntity, RegionEntity>(RegionSpecs.REGION, JoinType.LEFT)

        builder.likeAny(
            pattern,
            location.get(NAME),
            location.get(NAME_ALT),
            location.get(NAME_EN),
            region.get(RegionSpecs.NAME),
            region.get(RegionSpecs.NAME_EN),
            region.get(RegionSpecs.FULL_NAME),
            region.get(RegionSpecs.UNOFFICIAL_NAME),
            region.get(RegionSpecs.DISTRICT),
        )
    }

    fun bySort(
        column: LocationSortColumn,
        direction: Sort.Direction,
    ): Specification<LocationEntity> = Specification { location, select, builder ->
        val order =
            when (column) {
                LocationSortColumn.LOCATION -> {
                    val expression = location.get<String>(NAME)

                    if (direction == Sort.Direction.ASC) {
                        builder.asc(expression)
                    } else {
                        builder.desc(expression)
                    }
                }

                LocationSortColumn.REGION -> {
                    val region = location.join<LocationEntity, RegionEntity>(RegionSpecs.REGION, JoinType.LEFT)
                    val expression = region.get<String>(RegionSpecs.NAME)

                    if (direction == Sort.Direction.ASC) {
                        builder.asc(expression)
                    } else {
                        builder.desc(expression)
                    }
                }

                LocationSortColumn.POPULATION -> {
                    val region = location.join<LocationEntity, RegionEntity>(RegionSpecs.REGION, JoinType.LEFT)
                    val expression = region.get<Long>(RegionSpecs.POPULATION)

                    if (direction == Sort.Direction.ASC) {
                        builder.asc(expression)
                    } else {
                        builder.desc(expression)
                    }
                }
            }

        select.orderBy(order)
        null
    }
}