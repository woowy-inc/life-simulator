package ru.woowy.user.application.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.woowy.domain.model.UserPrincipal
import ru.woowy.extension.notFound
import ru.woowy.user.application.usecase.GetUserByUsernameUseCase

@Service
internal class UserDetailsServiceImpl(
    private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = getUserByUsernameUseCase(username) ?: notFound("User[username:$username] not found")

        return UserPrincipal(user.id, user.username, user.role)
    }
}