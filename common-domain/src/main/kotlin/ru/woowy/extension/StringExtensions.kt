package ru.woowy.extension

fun String.queryFormat(): String = "%${this.lowercase().trim()}%"