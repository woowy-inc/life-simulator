package ru.woowy.domain.usecase

import ru.woowy.domain.model.RefreshTokenRequest
import ru.woowy.domain.model.TokenDto
import ru.woowy.domain.model.UsernameRequest

interface AuthUseCase {
    fun refreshAccessToken(request: RefreshTokenRequest): TokenDto

    fun loginByUsername(request: UsernameRequest): TokenDto
}