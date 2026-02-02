package ru.woowy.application.service

import org.springframework.stereotype.Service
import ru.woowy.domain.Event
import ru.woowy.domain.service.WorldService

@Service
class WorldServiceImpl : WorldService {
    override fun processWorldTick(event: Event.WorldTickEvent) {
        println("Received event: $event")
    }
}