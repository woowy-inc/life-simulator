package ru.woowy.application.service

import org.springframework.stereotype.Service
import ru.woowy.domain.DomainEvent
import ru.woowy.domain.service.WorldService

@Service
class WorldServiceImpl : WorldService {
    override fun processWorldTick(event: DomainEvent.WorldTickEvent) {
        println("Received event: $event")
    }
}