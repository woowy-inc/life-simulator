package ru.woowy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TimeServiceApplication

fun main(args: Array<String>) {
    runApplication<TimeServiceApplication>(*args)
}