package ru.woowy.application.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.woowy.domain.model.UserPrincipal
import ru.woowy.domain.usecase.UserUseCase
import ru.woowy.extension.notFound
import java.util.UUID

@Service
class UserDetailsServiceImpl(
    private val userUseCase: UserUseCase,
) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val user =
            userUseCase.getByUserId(UUID.fromString(userId))
                ?: notFound("User[id:$userId] not found")

        return UserPrincipal(user.id, user.username, user.role)
    }
}