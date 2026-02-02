package ru.woowy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
internal class TimeServiceApplication

internal fun main(args: Array<String>) {
    runApplication<TimeServiceApplication>(*args)
}