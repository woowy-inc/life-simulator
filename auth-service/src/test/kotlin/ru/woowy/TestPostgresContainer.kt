package ru.woowy

import org.testcontainers.containers.PostgreSQLContainer

object TestPostgresContainer {
    val instance: PostgreSQLContainer<Nothing> =
        PostgreSQLContainer<Nothing>("postgres:18.1").apply {
            withReuse(true)
            start()
        }
}