package ru.woowy.extension

import org.slf4j.LoggerFactory

fun Any.classLogger() = LoggerFactory.getLogger(this::class.java)