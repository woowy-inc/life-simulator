package ru.woowy.domain.service

import ru.woowy.domain.Event

interface WorldService {
    fun processWorldTick(event: Event.WorldTickEvent)
}