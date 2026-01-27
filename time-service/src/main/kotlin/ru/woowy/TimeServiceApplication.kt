package ru.woowy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
internal class TimeServiceApplication

internal fun main(args: Array<String>) {
    runApplication<TimeServiceApplication>(*args)
}