package ru.woowy.domain.usecase

interface JwkUseCase {
    fun getJwks(): Map<String, Any>
}