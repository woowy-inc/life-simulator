package ru.woowy.application.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.woowy.application.usecase.GetUserByUsernameUseCase
import ru.woowy.extension.notFound
import ru.woowy.infrastructure.model.UserPrincipal

@Service
internal class UserDetailsServiceImpl(
    private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = getUserByUsernameUseCase(username) ?: notFound("User[username:$username] not found")

        return UserPrincipal(user)
    }
}