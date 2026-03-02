package ru.woowy.user.application.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.woowy.domain.model.UserPrincipal
import ru.woowy.extension.notFound
import ru.woowy.user.application.usecase.GetUserByIdUseCase
import java.util.UUID

@Service
internal class UserDetailsServiceImpl(
    private val getUserByIdUseCase: GetUserByIdUseCase,
) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val user =
            getUserByIdUseCase(UUID.fromString(userId))
                ?: notFound("User[id:$userId] not found")

        return UserPrincipal(user.id, user.username, user.role)
    }
}