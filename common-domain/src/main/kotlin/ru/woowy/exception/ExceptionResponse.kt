package ru.woowy.exception

data class ExceptionResponse(
    val error: String,
    val details: Map<String, String> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis(),
)