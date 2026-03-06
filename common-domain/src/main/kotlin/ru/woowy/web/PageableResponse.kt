package ru.woowy.web

abstract class PageableResponse<E> {
    abstract val page: Int
    abstract val totalPages: Int
    abstract val totalRecords: Long
    abstract val data: E
}