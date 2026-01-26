package ru.woowy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorldServiceApplication

fun main(args: Array<String>) {
    runApplication<WorldServiceApplication>(*args)
}