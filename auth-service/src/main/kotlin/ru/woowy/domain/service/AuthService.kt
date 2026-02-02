package ru.woowy.domain.service

import ru.woowy.domain.model.TokenResponse
import ru.woowy.domain.model.UsernameRequest

internal interface AuthService {
    fun authByUsername(request: UsernameRequest): TokenResponse

    fun getJwks(): Map<String, Any>
}