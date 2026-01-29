package ru.woowy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
internal class AuthServiceApplication

internal fun main(args: Array<String>) {
    runApplication<AuthServiceApplication>(*args)
}