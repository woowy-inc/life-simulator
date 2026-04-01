package ru.woowy.domain.model

interface TickableEvent : Event {
    val tickNumber: Long
}