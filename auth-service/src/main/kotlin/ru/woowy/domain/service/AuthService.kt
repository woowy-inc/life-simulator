package ru.woowy.domain.service

import ru.woowy.domain.model.TokenResponse
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.model.UsernameRequest
import ru.woowy.security.UserDto

internal interface AuthService {
    fun loginByUsername(request: UsernameRequest): TokenResponse

    fun registerUser(request: UserRegisterRequest): UserDto

    fun getJwks(): Map<String, Any>
}