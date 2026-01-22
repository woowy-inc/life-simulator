package ru.woowy.presentation.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
internal class TimeController {
    @GetMapping(Endpoint.GET_CURRENT_TIME)
    fun getTime(): ResponseEntity<Long> = ResponseEntity.ok(Instant.now().toEpochMilli())
}