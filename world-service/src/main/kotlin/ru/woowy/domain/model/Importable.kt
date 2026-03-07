package ru.woowy.domain.model

interface Importable<E> {
    fun import(data: List<E>): List<E>
}