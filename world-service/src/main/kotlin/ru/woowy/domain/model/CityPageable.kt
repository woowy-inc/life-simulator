package ru.woowy.domain.model

import ru.woowy.web.PageableResponse

data class CityPageable(
    override val page: Int,
    override val totalPages: Int,
    override val totalRecords: Long,
    override val data: List<City>,
) : PageableResponse<List<City>>()