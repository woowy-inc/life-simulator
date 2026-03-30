package ru.woowy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import java.util.TimeZone

@SpringBootApplication
@EnableDiscoveryClient
class NeedServiceApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

    runApplication<NeedServiceApplication>(*args)
}