package ru.woowy.domain.service

import ru.woowy.domain.DomainEvent

interface WorldService {
    fun processWorldTick(event: DomainEvent.WorldTickEvent)
}